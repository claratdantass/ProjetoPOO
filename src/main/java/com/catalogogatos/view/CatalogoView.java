package com.catalogogatos.view;

import com.catalogogatos.controller.MainController;
import com.catalogogatos.model.Gato;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.util.List;

public class CatalogoView {
    private final MainController controller;
    private final BorderPane root;
    private final TextField txtPesquisaNome;
    private final FlowPane galeriaGatos;
    private final CheckBox chkSexoMacho;
    private final CheckBox chkSexoFemea;
    private final TextField txtDescricao;

    public CatalogoView(MainController controller) {
        this.controller = controller;
        this.root = new BorderPane();
        this.txtPesquisaNome = new TextField();
        this.galeriaGatos = new FlowPane();
        this.chkSexoMacho = new CheckBox("Macho");
        this.chkSexoFemea = new CheckBox("Fêmea");
        this.txtDescricao = new TextField();

        setupUI();
        atualizarCatalogo();
    }

    private void exibirAlerta(String titulo, String mensagem, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private void setupUI() {
        VBox mainContainer = new VBox(20);
        mainContainer.setPadding(new Insets(20));

        HBox pesquisaBox = new HBox(10);
        pesquisaBox.setAlignment(Pos.CENTER);

        txtPesquisaNome.setPromptText("Pesquisa por nome");
        txtPesquisaNome.setPrefWidth(200);
        
        txtDescricao.setPromptText("Descrição (opcional)");
        txtDescricao.setPrefWidth(200);

        Button btnPesquisar = new Button("Pesquisar");
        btnPesquisar.setOnAction(e -> pesquisarGatos());

        HBox sexoBox = new HBox(10, chkSexoMacho, chkSexoFemea);
        sexoBox.setAlignment(Pos.CENTER);

        pesquisaBox.getChildren().addAll(txtPesquisaNome, txtDescricao, sexoBox, btnPesquisar);

        galeriaGatos.setHgap(20);
        galeriaGatos.setVgap(20);
        galeriaGatos.setPadding(new Insets(20));
        galeriaGatos.setAlignment(Pos.CENTER);

        ScrollPane scrollPane = new ScrollPane(galeriaGatos);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        mainContainer.getChildren().addAll(pesquisaBox, scrollPane);
        mainContainer.setStyle("-fx-background-color: #cce6ff;");

        root.setCenter(mainContainer);
    }

    public void atualizarCatalogo() {
        galeriaGatos.getChildren().clear();

        List<Gato> gatos = controller.getGatosObservable();

        if (gatos.isEmpty()) {
            Label lblVazio = new Label("Nenhum gato cadastrado.");
            lblVazio.setStyle("-fx-font-size: 16px;");
            galeriaGatos.getChildren().add(lblVazio);
        } else {
            for (Gato gato : gatos) {
                galeriaGatos.getChildren().add(criarCardGato(gato));
            }
        }
    }

    private VBox criarCardGato(Gato gato) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(10));
        card.setAlignment(Pos.CENTER);
        card.setPrefWidth(200);
        card.setStyle("-fx-background-color: white; -fx-border-color: #99ccff; -fx-border-radius: 5;");

        ImageView imageView = new ImageView();
        imageView.setFitWidth(150);
        imageView.setFitHeight(150);
        imageView.setPreserveRatio(true);

        try {
            if (gato.getCaminhoImagem() != null && !gato.getCaminhoImagem().equals("sem_imagem.png")) {
                File file = new File(gato.getCaminhoImagem());
                if (file.exists()) {
                    imageView.setImage(new Image(file.toURI().toString()));
                } else {
                    imageView.setImage(new Image(getClass().getResourceAsStream("/placeholder.png")));
                }
            } else {
                imageView.setImage(new Image(getClass().getResourceAsStream("/placeholder.png")));
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar imagem: " + e.getMessage());
            Rectangle placeholder = new Rectangle(150, 150);
            placeholder.setStyle("-fx-fill: lightgray; -fx-stroke: gray; -fx-stroke-width: 1;");
            card.getChildren().add(placeholder);
        }

        if (imageView.getImage() != null) {
            card.getChildren().add(imageView);
        }

        Label lblNome = new Label(gato.getNome());
        lblNome.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        Button btnDetalhes = new Button("Detalhes");
        btnDetalhes.setOnAction(e -> abrirDetalhesGato(gato));

        card.getChildren().addAll(lblNome, btnDetalhes);

        return card;
    }

    private void abrirDetalhesGato(Gato gato) {
        if (gato == null) {
            exibirAlerta("Erro", "Gato não encontrado.", Alert.AlertType.ERROR);
            return;  
        }

        DetalhesView detalhesView = new DetalhesView(controller, gato);

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Detalhes do Gato");
        dialog.getDialogPane().setContent(detalhesView.getRoot());
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.setResizable(true);
        dialog.getDialogPane().setPrefSize(600, 400);

        dialog.setOnCloseRequest(event -> atualizarCatalogo());

        dialog.showAndWait();
    }


    private void pesquisarGatos() {
        String nome = txtPesquisaNome.getText().trim();
        String descricao = txtDescricao.getText().trim();
        boolean sexoMacho = chkSexoMacho.isSelected();
        boolean sexoFemea = chkSexoFemea.isSelected();

        galeriaGatos.getChildren().clear();

        List<Gato> resultados = controller.buscarGatosPorFiltros(nome, sexoMacho, sexoFemea, descricao);

        if (resultados.isEmpty()) {
            Label lblVazio = new Label("Nenhum gato encontrado com esses filtros.");
            lblVazio.setStyle("-fx-font-size: 16px;");
            galeriaGatos.getChildren().add(lblVazio);
        } else {
            for (Gato gato : resultados) {
                galeriaGatos.getChildren().add(criarCardGato(gato));
            }
        }
    }


    public BorderPane getRoot() {
        return root;
    }
}
