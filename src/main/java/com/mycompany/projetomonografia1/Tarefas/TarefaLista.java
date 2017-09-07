package com.mycompany.projetomonografia1.Tarefas;

import com.mycompany.projetomonografia1.Modelo.Imagem;
import com.mycompany.projetomonografia1.TrataImagem;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vinicius
 */
public class TarefaLista implements Runnable {

    Imagem img;
    List<String> imgsProcessaveis;

    public TarefaLista(Imagem img, List<String> imgsProcessaveis) {
        this.img = img;
        this.imgsProcessaveis = imgsProcessaveis;
    }

    @Override
    public void run() {
        while (true) {
            try {
                img.tarefasLista(imgsProcessaveis);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

    }
}
