package com.mycompany.projetomonografia1;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionScopes;
import com.mycompany.projetomonografia1.Modelo.Imagem;
import com.mycompany.projetomonografia1.Tarefas.TarefaPeriodica;
import com.mycompany.projetomonografia1.Tarefas.TarefaVision;

import java.io.IOException;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

public class Teste {

    private static final String APPLICATION_NAME = "VisionLabelSample/1.0";

    public static void main(String[] args) throws IOException, GeneralSecurityException {
        Imagem img = new Imagem("/home/vinicius/NetBeansProjects/projetoMonografia1/img-");
//        //ExecutorService threadPool = Executors.newFixedThreadPool(4);
        
        TrataImagem ti = new TrataImagem(getVisionService());

        List<String> imgsProcessaveis = new ArrayList();
         Runnable tarefaVision = new TarefaVision(img, imgsProcessaveis);
        new Thread(tarefaVision).start();
        TarefaPeriodica mt = new TarefaPeriodica(img,ti, imgsProcessaveis);
        mt.tarefa();
        
       
       
       

//        Runnable tarefaVision = new TarefaVision(img, ti, imgsProcessaveis);
//        //threadPool.execute(tarefaVision);
//        new Thread(tarefaVision).start();
//        
    }

    public static Vision getVisionService() throws IOException, GeneralSecurityException {
        GoogleCredential credential = GoogleCredential.getApplicationDefault()
                .createScoped(VisionScopes.all());
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        return new Vision.Builder(GoogleNetHttpTransport.newTrustedTransport(), jsonFactory, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
}
/*OBSERVAÇÕES EM: 
    - TarefaPeriodica
    - TarefaVision
    - HOUVE PROBLEMAS AO TENTAR USAR THREADPOOL*/
