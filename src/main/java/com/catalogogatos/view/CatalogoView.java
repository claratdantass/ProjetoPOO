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
        VBox mainContainer = new VBox(24);
        mainContainer.setPadding(new Insets(30, 40, 30, 40));
        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.setStyle("-fx-background-color: #f7f7f7;");

        HBox pesquisaBox = new HBox(14);
        pesquisaBox.setAlignment(Pos.CENTER);
        pesquisaBox.setStyle("-fx-background-color: #fff; -fx-padding: 18 24 18 24; -fx-border-color: #e0e0e0; -fx-border-width: 1px;");

        txtPesquisaNome.setPromptText("Pesquisar por nome");
        txtPesquisaNome.setPrefWidth(180);
        txtPesquisaNome.setStyle("-fx-background-color: #fff; -fx-border-color: #e0e0e0; -fx-border-width: 1px;");

        txtDescricao.setPromptText("Descrição (opcional)");
        txtDescricao.setPrefWidth(180);
        txtDescricao.setStyle("-fx-background-color: #fff; -fx-border-color: #e0e0e0; -fx-border-width: 1px;");

        Button btnPesquisar = new Button("Pesquisar");
        btnPesquisar.setOnAction(e -> pesquisarGatos());
        btnPesquisar.setStyle("-fx-background-color: rgb(60, 154, 231); -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 7px; -fx-padding: 7 18;");

        HBox sexoBox = new HBox(12, chkSexoMacho, chkSexoFemea);
        sexoBox.setAlignment(Pos.CENTER);
        chkSexoMacho.setStyle("-fx-text-fill: #444; -fx-font-weight: normal;");
        chkSexoFemea.setStyle("-fx-text-fill: #444; -fx-font-weight: normal;");

        pesquisaBox.getChildren().addAll(txtPesquisaNome, txtDescricao, sexoBox, btnPesquisar);

        galeriaGatos.setHgap(18);
        galeriaGatos.setVgap(18);
        galeriaGatos.setPadding(new Insets(16));
        galeriaGatos.setAlignment(Pos.CENTER);

        ScrollPane scrollPane = new ScrollPane(galeriaGatos);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        mainContainer.getChildren().addAll(pesquisaBox, scrollPane);
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
        card.setPadding(new Insets(12));
        card.setAlignment(Pos.CENTER);
        card.setPrefWidth(170);
        card.setStyle("-fx-background-color: #fff; -fx-border-color: #e0e0e0; -fx-border-width: 1px; -fx-effect: dropshadow(gaussian, #e0e0e0, 2, 0, 0, 1);");

        ImageView imageView = new ImageView();
        imageView.setFitWidth(120);
        imageView.setFitHeight(120);
        imageView.setPreserveRatio(false);
        imageView.setStyle("-fx-background-color: #fafafa; -fx-border-color: #e0e0e0; -fx-border-width: 1px;");

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
            Rectangle placeholder = new Rectangle(120, 120);
            placeholder.setStyle("-fx-fill: #eaf6fb; -fx-stroke: rgba(242,148,230,0.45); -fx-stroke-width: 2;");
            card.getChildren().add(placeholder);
        }

        card.getChildren().add(imageView);

        Label lblNome = new Label(gato.getNome());
        lblNome.setStyle("-fx-font-weight: bold; -fx-font-size: 15px; -fx-text-fill: #3c9ae7;");

        Button btnDetalhes = new Button("Detalhes");
        btnDetalhes.setOnAction(e -> abrirDetalhesGato(gato));
        btnDetalhes.setStyle("-fx-background-color: rgb(179, 32, 159); -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 7px; -fx-padding: 6 18;");

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
