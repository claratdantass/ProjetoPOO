package com.catalogogatos.service;

import com.catalogogatos.model.Gato;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GatoService {
    private static final String JDBC_URL = "jdbc:postgresql://127.0.0.1:5432/poo_postegres";
    private static final String JDBC_USER = "SEU_USUARIO_AQUI";
    private static final String JDBC_PASSWORD = "SENHA_AQUI";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
    }

    public Gato adicionarGato(Gato gato) {
        String sql = "INSERT INTO animais (nome, idade, sexo, descricao, saude, vacinado, castrado, imagem, disponivel_para_adocao, caminho_imagem) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, gato.getNome());
            stmt.setInt(2, gato.getIdade());
            stmt.setString(3, gato.getSexo());
            stmt.setString(4, gato.getDescricao());
            stmt.setString(5, gato.getSaude());
            stmt.setBoolean(6, gato.isVacinado());
            stmt.setBoolean(7, gato.isCastrado());
            stmt.setBytes(8, gato.getImagem()); 
            stmt.setBoolean(9, gato.isDisponivelParaAdocao());
            stmt.setString(10, gato.getCaminhoImagem()); 
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                gato.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gato;
    }

    public List<Gato> obterTodosGatos() {
        List<Gato> gatos = new ArrayList<>();
        String sql = "SELECT * FROM animais";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Gato gato = mapResultSetToGato(rs);
                gatos.add(gato);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gatos;
    }

    public Optional<Gato> buscarPorId(Long id) {
        String sql = "SELECT * FROM animais WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToGato(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public boolean atualizarGato(Gato gato) {
        String sql = "UPDATE animais SET nome = ?, descricao = ?, sexo = ?, castrado = ?, disponivel_para_adocao = ?, caminho_imagem = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, gato.getNome());
            stmt.setString(2, gato.getDescricao());
            stmt.setString(3, gato.getSexo());
            stmt.setBoolean(4, gato.isCastrado());
            stmt.setBoolean(5, !gato.isAdotado());
            stmt.setString(6, gato.getCaminhoImagem());
            stmt.setLong(7, gato.getId());
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean excluirGato(Long id) {
        String sql = "DELETE FROM animais WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Gato> buscarPorNome(String nome) {
        List<Gato> gatos = new ArrayList<>();
        String sql = "SELECT * FROM animais WHERE LOWER(nome) LIKE ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + nome.toLowerCase() + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    gatos.add(mapResultSetToGato(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gatos;
    }

    public List<Gato> buscarPorLocal(String local) {
        List<Gato> gatos = new ArrayList<>();
        String sql = "SELECT * FROM animais WHERE LOWER(local) LIKE ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + local.toLowerCase() + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    gatos.add(mapResultSetToGato(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gatos;
    }

    private Gato mapResultSetToGato(ResultSet rs) throws SQLException {
        Gato gato = new Gato();
        gato.setId(rs.getLong("id"));
        gato.setNome(rs.getString("nome"));
        gato.setDescricao(rs.getString("descricao"));
        gato.setSexo(rs.getString("sexo"));
        gato.setCastrado(rs.getBoolean("castrado"));

        gato.setLocal("");
        gato.setCorPelagem("");
        gato.setCaminhoImagem(null);

        gato.setCaminhoImagem(rs.getString("caminho_imagem"));

        boolean disponivel = rs.getBoolean("disponivel_para_adocao");
        gato.setAdotado(!disponivel);
        return gato;
    }
}