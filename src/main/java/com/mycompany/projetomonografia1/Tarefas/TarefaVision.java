package com.mycompany.projetomonografia1.Tarefas;

import com.mycompany.projetomonografia1.Modelo.Imagem;
import com.mycompany.projetomonografia1.TrataImagem;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TarefaVision implements Runnable {

    Imagem img;
    TrataImagem ti;
    List<String> imgsProcessaveis;

    public TarefaVision(Imagem img, TrataImagem ti, List<String> imgsProcessaveis) {
        this.img = img;
        this.ti = ti;
        this.imgsProcessaveis = imgsProcessaveis;
    }

    @Override
    public void run() {
        List<String> listManipula;

        while (true) {

            synchronized (imgsProcessaveis) {
                if (imgsProcessaveis.isEmpty()) {
                    System.out.println("lista de imgs vazias não tem o que processar");
                    /*Para que a imgsProcessaveis não seja manipulada por mais
                    de uma thread, é preciso esperar que ela seja preenchida com 
                    imagens para que se faça as requisições*/
                    try {
                        imgsProcessaveis.wait();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(TarefaVision.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                System.out.println("tem imagem para processar");
                listManipula = imgsProcessaveis;
                listManipula.forEach(str -> {
                    System.out.println("--------------------------------------");
                    try {
                        TrataImagem.exibeResultado(ti.constroiImg(str));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    img.apagaImg(str);
                });

                /*Garantia que a lista esteja vazia para poder ser preenchida 
                por novas imgs. Lembre-se que a tarefaLista() só preenche a 
                imgsProcessaveis se a mesma estiver vazia.*/
                imgsProcessaveis.clear();
                listManipula.clear();

            }
        }

    }
}
