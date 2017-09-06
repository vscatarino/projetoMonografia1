package com.mycompany.projetomonografia1.Tarefas;

import com.mycompany.projetomonografia1.Modelo.Imagem;
import com.mycompany.projetomonografia1.TrataImagem;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;

public class TarefaPeriodica {

    Imagem img;
    TrataImagem ti;
    List<String> imgsProcessaveis;
    //ExecutorService threadPool;
    public static final long TEMPO = (1000 * 10); // refaz a tarefa a cada 10 segundos.

    public TarefaPeriodica(Imagem img,TrataImagem ti, List<String> imgsProcessaveis/*, ExecutorService threadPool*/) {
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
                            img.tarefaProcuraImg();
                            img.tarefaMontaListaExecutavel(imgsProcessaveis);
                            for(int i = 0; i < imgsProcessaveis.size();i++){
                                String str = imgsProcessaveis.get(i);
                                Path path = Paths.get(imgsProcessaveis.get(i));
                                TrataImagem.exibeResultado(ti.constroiImg(path));
                            }
                            
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            timer.scheduleAtFixedRate(tarefa, TEMPO, TEMPO);
        }
    }
    
  
}

/*FAZER COM QUE AS TAREFAS SEJAM SEQUENCIAIS AQUI DENTRO, AO INVÉS DE THREAD?*/
