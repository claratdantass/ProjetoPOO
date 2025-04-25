package com.catalogogatos.view;

import com.catalogogatos.controller.MainController;
import com.catalogogatos.model.Gato;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class DetalhesView {
    private final MainController controller;
    private final BorderPane root;
    private final Gato gato;
    private final TextField txtNome;
    private final TextField txtLocal;
    private final TextArea txtDescricao;
    private final ImageView imageView;
    private File selectedFile;
    
    private final TextField txtCorPelagem;
    private final CheckBox chkAdotado;
    private final CheckBox chkCastrado;
    private final RadioButton rbtnMacho;
    private final RadioButton rbtnFemea;
    
    public DetalhesView(MainController controller, Gato gato) {
        this.controller = controller;
        this.gato = gato;
        this.root = new BorderPane();
        this.txtNome = new TextField(gato.getNome());
        this.txtLocal = new TextField(gato.getLocal());
        this.txtDescricao = new TextArea(gato.getDescricao());
        this.imageView = new ImageView();

        System.out.println("Detalhes do gato: " + gato);

        this.txtCorPelagem = new TextField(gato.getCorPelagem());
        this.chkAdotado = new CheckBox("Adotado");
        this.chkCastrado = new CheckBox("Castrado");

        ToggleGroup sexoGroup = new ToggleGroup();
        this.rbtnMacho = new RadioButton("Macho");
        this.rbtnFemea = new RadioButton("Fêmea");
        rbtnMacho.setToggleGroup(sexoGroup);
        rbtnFemea.setToggleGroup(sexoGroup);

        setupUI();
        carregarImagem();
        carregarDadosAdicionais();
    }
    
    private void setupUI() {

        txtNome.setPrefWidth(250);
        txtLocal.setPrefWidth(250);
        txtCorPelagem.setPrefWidth(250);
        txtDescricao.setPrefWidth(250);
    
        GridPane.setHgrow(txtNome, Priority.ALWAYS);
        GridPane.setHgrow(txtLocal, Priority.ALWAYS);
        GridPane.setHgrow(txtCorPelagem, Priority.ALWAYS);
        GridPane.setHgrow(txtDescricao, Priority.ALWAYS);
    
        VBox mainContainer = new VBox(10);
        mainContainer.setPadding(new Insets(20));
        mainContainer.setAlignment(Pos.CENTER);
    
        HBox contentContainer = new HBox(20);
        contentContainer.setAlignment(Pos.CENTER);
    
        VBox imageContainer = new VBox(10);
        imageContainer.setAlignment(Pos.CENTER);
    
        imageView.setFitWidth(200);
        imageView.setFitHeight(200);
        imageView.setPreserveRatio(true);
    
        imageContainer.getChildren().add(imageView);
    
        Button btnTrocarImagem = new Button("Trocar Imagem");
        btnTrocarImagem.setOnAction(e -> selecionarImagem());
    
        imageContainer.getChildren().add(btnTrocarImagem);
    
        VBox infoContainer = new VBox(15);
        infoContainer.setAlignment(Pos.TOP_LEFT); 
    
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
    
        form.add(new Label("Nome:"), 0, 0);
        form.add(txtNome, 1, 0);
    
        form.add(new Label("Local:"), 0, 1);
        form.add(txtLocal, 1, 1);
    
        form.add(new Label("Cor da Pelagem:"), 0, 2);
        form.add(txtCorPelagem, 1, 2);
    
        form.add(new Label("Sexo:"), 0, 3);
        HBox sexoContainer = new HBox(10, rbtnMacho, rbtnFemea);
        sexoContainer.setAlignment(Pos.CENTER_LEFT);
        form.add(sexoContainer, 1, 3);
    
        form.add(chkAdotado, 1, 4);
        form.add(chkCastrado, 1, 5);
    
        form.add(new Label("Descrição:"), 0, 6);
        form.add(txtDescricao, 1, 6);
        txtDescricao.setPrefRowCount(4);
    
        infoContainer.getChildren().add(form);
    
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
    
        Button btnEditar = new Button("Editar");
        btnEditar.setOnAction(e -> editarGato());
        btnEditar.setStyle("-fx-background-color: #99ccff;");
    
        Button btnExcluir = new Button("Excluir");
        btnExcluir.setOnAction(e -> excluirGato());
        btnExcluir.setStyle("-fx-background-color: rgb(179, 32, 159);");
    
        buttonBox.getChildren().addAll(btnEditar, btnExcluir);
    
        infoContainer.getChildren().add(buttonBox);
    
        contentContainer.getChildren().addAll(imageContainer, infoContainer);
    
        mainContainer.getChildren().add(contentContainer);
        mainContainer.setStyle("-fx-background-color: #cce6ff;");
    
        root.setCenter(mainContainer);
    }

    private void carregarImagem() {
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
        }
    }


    private void carregarDadosAdicionais() {
        if (gato.isAdotado()) {
            chkAdotado.setSelected(true);
        }
        
        if (gato.isCastrado()) {
            chkCastrado.setSelected(true);
        }
        
        if (gato.getSexo().equals("Macho")) {
            rbtnMacho.setSelected(true);
        } else {
            rbtnFemea.setSelected(true);
        }
    }
    
    private void selecionarImagem() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecionar Imagem");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imagens", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        
        selectedFile = fileChooser.showOpenDialog(root.getScene().getWindow());
        if (selectedFile != null) {
            try {
                Image image = new Image(selectedFile.toURI().toString());
                imageView.setImage(image);
            } catch (Exception e) {
                exibirAlerta("Erro ao carregar imagem", e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }
    
    private void editarGato() {
        String nome = txtNome.getText().trim();
        String local = txtLocal.getText().trim();
        String descricao = txtDescricao.getText().trim();
        
        if (nome.isEmpty() || local.isEmpty()) {
            exibirAlerta("Campos obrigatórios", "Nome e Local são campos obrigatórios.", Alert.AlertType.WARNING);
            return;
        }
        
        try {
            if (selectedFile != null) {
                Path diretorioImagens = Paths.get("imagens");
                if (!Files.exists(diretorioImagens)) {
                    Files.createDirectories(diretorioImagens);
                }
                
                String extensao = selectedFile.getName().substring(selectedFile.getName().lastIndexOf("."));
                String nomeArquivo = "gato_" + System.currentTimeMillis() + extensao;
                Path destino = diretorioImagens.resolve(nomeArquivo);
                
                Files.copy(selectedFile.toPath(), destino, StandardCopyOption.REPLACE_EXISTING);
                gato.setCaminhoImagem(destino.toString());
            }
            
            gato.setNome(nome);
            gato.setLocal(local);
            gato.setDescricao(descricao);
            gato.setCorPelagem(txtCorPelagem.getText().trim());
            gato.setAdotado(chkAdotado.isSelected());
            gato.setCastrado(chkCastrado.isSelected());
            gato.setSexo(rbtnMacho.isSelected() ? "Macho" : "Fêmea");
            
            controller.atualizarGato(gato);
            
            exibirAlerta("Sucesso", "Gato atualizado com sucesso!", Alert.AlertType.INFORMATION);
            
        } catch (Exception e) {
            exibirAlerta("Erro ao atualizar", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    
    private void excluirGato() {
        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmar exclusão");
        confirmacao.setHeaderText(null);
        confirmacao.setContentText("Tem certeza que deseja excluir o gato " + gato.getNome() + "?");
        
        confirmacao.showAndWait().ifPresent(resposta -> {
            if (resposta == ButtonType.OK) {
                controller.excluirGato(gato.getId());
                
                root.getScene().getWindow().hide();
            }
        });
    }
    
    private void exibirAlerta(String titulo, String mensagem, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
    
    public BorderPane getRoot() {
        return root;
    }
}
