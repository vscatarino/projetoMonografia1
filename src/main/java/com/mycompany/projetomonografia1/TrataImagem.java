package com.mycompany.projetomonografia1;

import com.google.common.collect.ImmutableList;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.model.AnnotateImageRequest;
import com.google.api.services.vision.v1.model.AnnotateImageResponse;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
import com.google.api.services.vision.v1.model.EntityAnnotation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 *
 * @author vcata
 */
public class TrataImagem {

    protected Vision vision;

    public TrataImagem(Vision vision) {
        this.vision = vision;
    }

    private byte[] carregaImagem(String fileName) throws IOException {
        Path path = Paths.get(fileName);
        byte[] data = Files.readAllBytes(path); //readAllBytes lê todos os bytes de path.
        return data;
    }

    public List<EntityAnnotation> constroiImg(String fileName) throws IOException {
        byte[] img = carregaImagem(fileName);

        AnnotateImageRequest request = new AnnotateImageRequest()
                .setImage(new com.google.api.services.vision.v1.model.Image().encodeContent(img))
                .setFeatures(ImmutableList.of(
                        new com.google.api.services.vision.v1.model.Feature()
                                .setType("LABEL_DETECTION")
                                .setMaxResults(10)));
        Vision.Images.Annotate annotate = vision.images()
                .annotate(new BatchAnnotateImagesRequest().setRequests(ImmutableList.of(request)));
        // Due to a bug: requests to Vision API containing large images fail when GZipped.
        annotate.setDisableGZipContent(true);

        BatchAnnotateImagesResponse batchResponse = annotate.execute();
        assert batchResponse.getResponses().size() == 1;
        AnnotateImageResponse response = batchResponse.getResponses().get(0);
        if (response.getLabelAnnotations() == null) {
            throw new IOException(
                    response.getError() != null
                    ? response.getError().getMessage()
                    : "Unknown error getting image annotations");
        }
        return response.getLabelAnnotations();
    }

    public static void exibeResultado(List<EntityAnnotation> labels) {
        /*Esse método será o responsável por realizar as comparações, ao invés
        de ficar exibindo os resultados*/
        for (EntityAnnotation label : labels) {
            System.out.printf(
                    "\t%s (score: %.3f)\n",
                    label.getDescription(),
                    label.getScore());
        }
        if (labels.isEmpty()) {
            System.out.println("\tNo labels found.");
            //label vazio é uma ótima opção para passar o controle para o usuário
        }
    }
}
