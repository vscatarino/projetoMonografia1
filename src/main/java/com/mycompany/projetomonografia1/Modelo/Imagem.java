package com.mycompany.projetomonografia1.Modelo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

public class Imagem {

    private final File f;
    private List<String> listaImgs;

    public Imagem(String path) {
        //f referencia ao diretório img
        this.f = new File(path);
        this.listaImgs = new ArrayList<>();
    }

    /*Adiciono todas as imagens, do diretório img-, em uma lista. 
        Para ficar mais fácil de manipular.*/
    private void addImgens() {
        //paths é um array que guardará o caminhos de todas as imagens armazenadas em path(diretório img).
        File[] paths = f.listFiles();
        for (File p : paths) {
            listaImgs.add(p.toString());
        }
    }
    
    /*Neste método eu elaboro a string que define o caminho de destino de uma
        imagem. O novo caminho de cada imagem é adicionado a uma nova lista, e 
        apagado do diretório img-. Desta forma eu garanto que cada imagem seja 
        adicionada a uma lista, a qual será analisada, apenas uma vez.*/
     private void moveToAddList(String str, List<String> novaLista) {
        int index = str.lastIndexOf("-");

        String prefixo = str.substring(0, index - 3);
        String sufixo = str.substring(index + 1);
        
        String nova = prefixo + "AddLista" + sufixo;
        
        File origem = new File(str);
        File destino = new File(nova);
        try {
            copia(origem, destino);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        novaLista.add(nova);
        System.out.println("img adicionada:" + nova);
        origem.delete();
    }
    /*Método responsável por mover uma imagem de um diretório para o outro.*/
    private void copia(File origem, File destino) throws FileNotFoundException, IOException {
        if (destino.exists()) {
            destino.delete();
        }
        FileChannel sourceChannel = null;
        FileChannel destinationChannel = null;
        try {
            sourceChannel = new FileInputStream(origem).getChannel();
            destinationChannel = new FileOutputStream(destino).getChannel();
            sourceChannel.transferTo(0, sourceChannel.size(),
                    destinationChannel);
        } finally {
            if (sourceChannel != null && sourceChannel.isOpen()) {
                sourceChannel.close();
            }
            if (destinationChannel != null && destinationChannel.isOpen()) {
                destinationChannel.close();
            }
        }
    }
    /*Este método será o responsável por verificar se há imagens no diretório img-*/
    public synchronized void tarefaProcuraImg() {       
        addImgens();        
        listaImgs.forEach(System.out::println);
        if (!listaEhCheia()) {
            System.out.println("diretório sem imagens!");
        } else {
            System.out.println("diretório tem imagens... notificando");
           this.notify();
        }
    }
    
    /*Este método é reponsável por construir uma lista consistente 
        para a análise de imagens. Ao copiar as imagens da primeira lista,
        a mesma é limpa para que não ocorra NullPointerException*/
    public void tarefaMontaListaExecutavel(List<String> novaLista) {
        this.listaImgs.forEach(img -> {
            String str = img;
            moveToAddList(str, novaLista);
        });
        listaImgs.clear();
    }
   
    public boolean listaEhCheia() {
        return !listaImgs.isEmpty();
    }
    /*Apaga as imagens analisadas do diretório AddList*/
    public void apagaImg(String str){
        File diretorio = new File(str);
        diretorio.delete();
    }    
}
