package com.mycompany.projetomonografia1.Tarefas;

import com.mycompany.projetomonografia1.Modelo.Imagem;
import com.mycompany.projetomonografia1.TrataImagem;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TarefaPeriodica {

    Imagem img;
    TrataImagem ti;
    List<String> imgsProcessaveis;
    //ExecutorService threadPool;
    public static final long TEMPO = (1000 * 5); // refaz a tarefa a cada 10 segundos.

    public TarefaPeriodica(Imagem img, TrataImagem ti, List<String> imgsProcessaveis/*, ExecutorService threadPool*/) {
        this.img = img;
        this.imgsProcessaveis = imgsProcessaveis;
        this.ti = ti;
        //this.threadPool = threadPool;
    }

    public void tarefa() {
        Timer timer = null;
        if (timer == null) {
            timer = new Timer();
            TimerTask tarefa = new TimerTask() {
                @Override
                public void run() {

                    try {

                        img.tarefasLista(imgsProcessaveis);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(TarefaPeriodica.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            };
            timer.scheduleAtFixedRate(tarefa, TEMPO, TEMPO);
        }
    }

}
