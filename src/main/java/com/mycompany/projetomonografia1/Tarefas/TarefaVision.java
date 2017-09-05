package com.mycompany.projetomonografia1.Tarefas;

import com.google.api.services.vision.v1.Vision;
import com.mycompany.projetomonografia1.Modelo.Imagem;
import java.util.List;

public class TarefaVision implements Runnable {

    Imagem img;
    Vision vision;
    //TrataImagem ti;
    List<String> imgsProcessaveis;

    public TarefaVision(Imagem img, /*TrataImagem ti,*/ List<String> imgsProcessaveis) {
        this.img = img;
        //this.ti = ti;
        this.imgsProcessaveis = imgsProcessaveis;
    }

    @Override
    public void run() {       
        
        while(true){
            for(int i=0 ; i < imgsProcessaveis.size(); i++){
                System.out.println(imgsProcessaveis.get(i));
                System.out.println("vlw");
            }
            //});
//                img.apagaImg(imgsProcessaveis.get(i));
//                imgsProcessaveis.remove(i);
            /*1 - CRIAR E UTILIZAR AS REGRAS DE INFERÊNCIA*/
        }
    }
}
