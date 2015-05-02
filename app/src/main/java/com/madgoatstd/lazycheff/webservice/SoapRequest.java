package com.madgoatstd.lazycheff.webservice;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.madgoatstd.lazycheff.database.Ingrediente;
import com.madgoatstd.lazycheff.database.IngredienteDataSource;
import com.madgoatstd.lazycheff.database.IngredienteRecetaDataSource;
import com.madgoatstd.lazycheff.database.Ingrediente_Receta;
import com.madgoatstd.lazycheff.database.Receta;
import com.madgoatstd.lazycheff.database.RecipeDataSource;
import com.madgoatstd.lazycheff.database.Utensilio;
import com.madgoatstd.lazycheff.database.UtensilioDataSource;
import com.madgoatstd.lazycheff.database.UtensilioRecetaDataSource;
import com.madgoatstd.lazycheff.database.Utensilio_Receta;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.IOException;
import java.net.Proxy;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class SoapRequest{
    private Context contexto;
    //metodo: 1=>getUltimosIngredientes, 2=>getUltimosUtensilios, 3=>getUltimasRecetas
    private Vector<SoapObject> resultado;
    public SoapRequest(Context contexto){
        this.contexto = contexto;
    }

    public void consumirWebService(){
        IngredienteDataSource ingredienteDataSource = new IngredienteDataSource(contexto);
        UtensilioDataSource utensilioDataSource = new UtensilioDataSource(contexto);
        RecipeDataSource recipeDataSource = new RecipeDataSource(contexto);

        ingredienteDataSource.open();
        utensilioDataSource.open();
        recipeDataSource.open();
        new LlamarWebService().execute("getUltimosIngredientes",String.valueOf(ingredienteDataSource.getLastId()));
        new LlamarWebService().execute("getUltimosUtensilios",String.valueOf(utensilioDataSource.getLastId()));
        new LlamarWebService().execute("getUltimasRecetas",String.valueOf(recipeDataSource.getLastId()));

        ingredienteDataSource.close();
        utensilioDataSource.close();
        recipeDataSource.close();

    }

    private void procesarUtensilios(){
        List<Utensilio> utensilios = new ArrayList<Utensilio>();
        UtensilioDataSource ingredienteDataSource = new UtensilioDataSource(contexto);
        ingredienteDataSource.open();
        if(resultado != null) {

            for (int i = 0; i < resultado.size(); i++) {
                ingredienteDataSource.createUtensilio(Integer.valueOf(resultado.get(i).getProperty("id").toString()), resultado.get(i).getProperty("nombre").toString());
                utensilios.add(new Utensilio(Integer.valueOf(resultado.get(i).getProperty("id").toString()), resultado.get(i).getProperty("nombre").toString()));
            }
        }
        ingredienteDataSource.close();
    }
    private void procesarIngredientes(){
        List<Ingrediente> ingredientes = new ArrayList<Ingrediente>();
        IngredienteDataSource ingredienteDataSource = new IngredienteDataSource(contexto);
        ingredienteDataSource.open();

        if(resultado != null) {
            for (int i = 0; i < resultado.size(); i++) {
                ingredienteDataSource.createIngrediente(Integer.valueOf(resultado.get(i).getProperty("id").toString()), resultado.get(i).getProperty("nombre").toString());
                ingredientes.add(new Ingrediente(Integer.valueOf(resultado.get(i).getProperty("id").toString()), resultado.get(i).getProperty("nombre").toString()));
            }
        }
        ingredienteDataSource.close();

    }
    private void procesarRecetas(){
        RecipeDataSource recetas = new RecipeDataSource(contexto);
        UtensilioRecetaDataSource utensilios= new UtensilioRecetaDataSource(contexto);
        IngredienteRecetaDataSource ingredientes = new IngredienteRecetaDataSource(contexto);

        recetas.open();
        ingredientes.open();
        utensilios.open();

        if(resultado != null) {
            for (int i = 0; i < resultado.size(); i++) {
                SoapObject item = resultado.get(i);
                recetas.createReceta(Integer.valueOf(item.getPropertyAsString("id")),
                        item.getProperty("nombre").toString(),
                        item.getPropertyAsString("dificultad"),
                        item.getPropertyAsString("tiempo"),
                        item.getPropertyAsString("indicaciones"),
                        item.getPropertyAsString("imagen"),
                        item.getPropertyAsString("tipo"));
                Vector<SoapObject> ing = (Vector<SoapObject>) item.getProperty("ingredientes");
                for (int j = 0; j < ing.size(); j++) {
                    SoapObject subitem = ing.get(j);
                    ingredientes.createIngredienteReceta(Integer.valueOf(subitem.getPropertyAsString("id")), Integer.valueOf(item.getPropertyAsString("id")), subitem.getPropertyAsString("cantidad"));

                }

                Vector<SoapObject> ut = (Vector<SoapObject>) item.getProperty("utensilios");
                for (int j = 0; j < ut.size(); j++) {
                    SoapObject subitem = ut.get(j);
                    utensilios.createUtensilioReceta(Integer.valueOf(subitem.getPropertyAsString("id")), Integer.valueOf(item.getPropertyAsString("id")));

                }
            }
        }
        recetas.close();
        utensilios.close();
        ingredientes.close();

    }

    private class LlamarWebService extends AsyncTask<String,String,Vector<SoapObject>> {
        ProgressDialog dialog;
        private static final String MAIN_REQUEST_URL = "http://www.madgoatstd.com/lazychef/recetas.php";
        HttpTransportSE ht = getHttpTransportSE();
        String metodo;

        private final SoapSerializationEnvelope getSoapSerializationEnvelope(SoapObject request) {
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = false;
            envelope.implicitTypes = true;
            envelope.setAddAdornments(false);
            envelope.setOutputSoapObject(request);
            return envelope;
        }

        private final HttpTransportSE getHttpTransportSE() {
            HttpTransportSE ht = new HttpTransportSE(Proxy.NO_PROXY,MAIN_REQUEST_URL,60000);
            ht.debug = true;
            ht.setXmlVersionTag("<!--?xml version=\"1.0\" encoding= \"UTF-8\" ?-->");
            return ht;
        }

        @Override
        protected Vector<SoapObject> doInBackground(String... params) {
            metodo = params[0];
            SoapObject request = new SoapObject("http://www.madgoatstd.com/lazychef/recetas.php",params[0]);
            request.addProperty("ultimo",params[1]);
            SoapSerializationEnvelope envelope = getSoapSerializationEnvelope(request);
            try{
                ht.call("http://www.madgoatstd.com/lazychef/recetas.php",envelope);
                SoapObject response = (SoapObject)envelope.bodyIn;
                Vector<SoapObject> responseVector = (Vector<SoapObject>) response.getProperty(0);
                return responseVector;
            }catch (SocketTimeoutException t) {
                t.printStackTrace();
            } catch (IOException i) {
                i.printStackTrace();
            } catch (Exception q) {
                q.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(contexto);
            dialog.setTitle("Obteniendo Recursos...");
            dialog.setMessage("Conectando con WebService...");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected void onPostExecute(Vector<SoapObject> result) {
            //Actualizar Base de Datos
            resultado = result;
            if(metodo == "getUltimosIngredientes"){
                procesarIngredientes();
            }
            if(metodo == "getUltimosUtensilios"){
                procesarUtensilios();
            }
            if(metodo == "getUltimasRecetas"){
                procesarRecetas();
            }
            dialog.dismiss();
        }
    }
}
