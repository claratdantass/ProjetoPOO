package com.catalogogatos.view;

import javafx.geometry.Insets;

import com.catalogogatos.controller.MainController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.layout.VBox;

public class MainView {
    private final MainController controller;
    private final BorderPane root;
    private final CadastroView cadastroView;
    private final CatalogoView catalogoView;
    private final StackPane contentPane;

    public MainView(MainController controller) {
        this.controller = controller;
        this.root = new BorderPane();
        this.cadastroView = new CadastroView(controller);
        this.catalogoView = new CatalogoView(controller);
        this.contentPane = new StackPane();

        setupUI();
    }

    private void setupUI() {
        BorderPane initialScreen = new BorderPane();

        Text title = new Text("Catálogo de Gatos UFPB");
        title.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-fill: #2c3e50;");

        Text subtitle = new Text("Adote, cadastre e conheça os felinos do campus!");
        subtitle.setStyle("-fx-font-size: 16px; -fx-font-style: italic; -fx-fill: #34495e;");

        
        Text description = new Text("Bem-vindo ao sistema de adoção e catalogação de gatos da UFPB. Aqui você pode cadastrar novos gatos, visualizar detalhes, e encontrar um novo amigo para adotar!");
        description.setStyle("-fx-font-size: 14px; -fx-fill: #555; -fx-text-alignment: center;");
        description.setWrappingWidth(400);


        Button btnCadastro = new Button("Cadastrar Gato");
        btnCadastro.setStyle("-fx-background-color:rgb(179, 32, 159); -fx-text-fill: white; -fx-font-size: 15px; -fx-font-weight: bold; -fx-padding: 10 32;");
        Button btnCatalogo = new Button("Ver Catálogo");
        btnCatalogo.setStyle("-fx-background-color: #2980b9; -fx-text-fill: white; -fx-font-size: 15px; -fx-font-weight: bold; -fx-padding: 10 32;");
        btnCadastro.setPrefWidth(200);
        btnCatalogo.setPrefWidth(200);

        btnCadastro.setOnAction(e -> mostrarCadastro());
        btnCatalogo.setOnAction(e -> mostrarCatalogo());

        VBox infoPane = new VBox(10);
        infoPane.setAlignment(Pos.CENTER);
        infoPane.getChildren().addAll(title, subtitle, description);
        try {
            java.net.URL imgUrl = getClass().getResource("/placeholder.png");
            if (imgUrl != null) {
                javafx.scene.image.ImageView logoView = new javafx.scene.image.ImageView(new javafx.scene.image.Image(imgUrl.toExternalForm()));
                logoView.setFitWidth(120);
                logoView.setPreserveRatio(true);
                infoPane.getChildren().add(logoView);
            }
        } catch (Exception ex) { /* ignora se não encontrar imagem */ }


        VBox buttonsPane = new VBox(15);
        buttonsPane.setAlignment(Pos.CENTER);
        buttonsPane.getChildren().addAll(btnCadastro, btnCatalogo);
        buttonsPane.setPadding(new javafx.geometry.Insets(10, 0, 0, 0));

        VBox centerPane = new VBox(30);
        centerPane.setAlignment(Pos.CENTER);
        centerPane.getChildren().addAll(infoPane, buttonsPane);

        initialScreen.setCenter(centerPane);

        Text footer = new Text("Desenvolvido por Emyle, Clara, Bea e Gustavo • UFPB 2025");
        footer.setStyle("-fx-font-size: 11px; -fx-fill: #888;");
        initialScreen.setBottom(footer);
        BorderPane.setAlignment(footer, Pos.CENTER);

        root.setCenter(initialScreen);
    }

    private void mostrarCadastro() {
        BorderPane cadastroScreen = new BorderPane();
        Button btnVoltarCadastro = new Button("Voltar");
        btnVoltarCadastro.setOnAction(e -> voltarTelaInicial());

        BorderPane.setMargin(btnVoltarCadastro, new Insets(12, 0, 16, 0));

        cadastroScreen.setTop(btnVoltarCadastro);
        cadastroScreen.setCenter(cadastroView.getRoot());

        root.setCenter(cadastroScreen);
    }

    private void mostrarCatalogo() {
        catalogoView.atualizarCatalogo();
        BorderPane catalogoScreen = new BorderPane();
        Button btnVoltarCatalogo = new Button("Voltar");
        btnVoltarCatalogo.setOnAction(e -> voltarTelaInicial());

        catalogoScreen.setTop(btnVoltarCatalogo);
        catalogoScreen.setCenter(catalogoView.getRoot());

        root.setCenter(catalogoScreen);
    }

    private void voltarTelaInicial() {
        setupUI(); 
    }

    public BorderPane getRoot() {
        return root;
    }
}
