import DAO.CategoriaDAO;
import DAO.ProdutoDAO;
import Modelo.Categoria;
import Modelo.Produto;

import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.util.List;

public class FormGerenciadorEstoque extends JFrame {
    private JPanel Principal;
    private JTextField TF_nome;
    private JTextField TF_quantidade;
    private JTextField TF_preco;
    private JButton adicionarButton;
    private JButton excluirButton;
    private JButton salvarAtualizarButton;
    private JTable table1;
    private JTextField TF_Total;
    private JButton cancelarButton;
    private JButton editarButton;
    private JButton categoriasButton;
    private JComboBox<Categoria> comboCategoria;

    ProdutoDAO produtoDao = new ProdutoDAO();
    static int idProdutoSelecionado;

    String[] colunas = {"ID", "PRODUTO", "CATEGORIA", "QUANTIDADE", "PREÇO", "TOTAL (R$)"};
    DefaultTableModel model = new DefaultTableModel(colunas, 0);

    public FormGerenciadorEstoque(){
        setContentPane(Principal);
        table1.setModel(model);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 600);
        setTitle("Gerenciador de Estoque");
        setLocationRelativeTo(null);

        habilitarCampos(false);
        listarProdutos();

        // Ações dos Botões
        categoriasButton.addActionListener(e -> {
            FormGerenciadorCategoria formCat = new FormGerenciadorCategoria();
            formCat.setVisible(true);
            // Ao fechar a janela de categorias, recarrega o combo (truque simples)
            formCat.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                    carregarCategorias();
                }
            });
        });

        adicionarButton.addActionListener(e -> {
            carregarCategorias();
            if(comboCategoria.getItemCount() == 0) {
                JOptionPane.showMessageDialog(null, "Cadastre uma Categoria primeiro!");
                return;
            }
            habilitarCampos(true);
            salvarAtualizarButton.setText("Salvar");
            TF_nome.requestFocus();
        });

        salvarAtualizarButton.addActionListener(e -> {
            salvarProduto();
        });

        editarButton.addActionListener(e -> {
            int linha = table1.getSelectedRow();
            if (linha != -1) {
                carregarCategorias();
                habilitarCampos(true);
                salvarAtualizarButton.setText("Atualizar");

                // Preencher dados
                idProdutoSelecionado = Integer.parseInt(table1.getValueAt(linha, 0).toString());
                TF_nome.setText(table1.getValueAt(linha, 1).toString());

                // Selecionar a categoria correta no Combo
                String nomeCatTabela = table1.getValueAt(linha, 2).toString();
                for (int i = 0; i < comboCategoria.getItemCount(); i++) {
                    if (comboCategoria.getItemAt(i).getNome_categoria().equals(nomeCatTabela)) {
                        comboCategoria.setSelectedIndex(i);
                        break;
                    }
                }

                TF_quantidade.setText(table1.getValueAt(linha, 3).toString());
                TF_preco.setText(table1.getValueAt(linha, 4).toString());
            } else {
                JOptionPane.showMessageDialog(null, "Selecione um produto para editar.");
            }
        });

        cancelarButton.addActionListener(e -> habilitarCampos(false));

        excluirButton.addActionListener(e -> {
            int linha = table1.getSelectedRow();
            if (linha != -1) {
                int id = Integer.parseInt(table1.getValueAt(linha, 0).toString());
                if(JOptionPane.showConfirmDialog(null, "Excluir produto?", "Confirmação", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                    produtoDao.excluir(id);
                    listarProdutos();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Selecione um produto para excluir.");
            }
        });
    }

    private void salvarProduto() {
        try {
            String nome = TF_nome.getText().trim();
            int qtd = Integer.parseInt(TF_quantidade.getText().trim());
            double preco = Double.parseDouble(TF_preco.getText().trim().replace(",", "."));
            Categoria cat = (Categoria) comboCategoria.getSelectedItem();

            if (nome.isEmpty() || cat == null) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos.");
                return;
            }

            Produto p = new Produto(nome, qtd, preco, cat);

            if (salvarAtualizarButton.getText().equals("Salvar")) {
                if(produtoDao.inserir(p)) JOptionPane.showMessageDialog(null, "Produto salvo!");
            } else {
                p.setId_produto(idProdutoSelecionado);
                if(produtoDao.atualizar(p)) JOptionPane.showMessageDialog(null, "Produto atualizado!");
            }

            habilitarCampos(false);
            listarProdutos();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Valores numéricos inválidos.");
        }
    }

    public void habilitarCampos(Boolean status){
        TF_nome.setEnabled(status);
        TF_quantidade.setEnabled(status);
        TF_preco.setEnabled(status);
        comboCategoria.setEnabled(status);

        salvarAtualizarButton.setEnabled(status);
        cancelarButton.setEnabled(status);

        adicionarButton.setEnabled(!status);
        editarButton.setEnabled(!status);
        excluirButton.setEnabled(!status);

        if(!status) limparCampos();
    }

    public void limparCampos(){
        TF_nome.setText("");
        TF_quantidade.setText("");
        TF_preco.setText("");
    }

    public void listarProdutos(){
        model.setRowCount(0);
        double totalGeral = 0;
        for(Produto p : produtoDao.listarTodos()){
            double totalItem = p.getQuantidade() * p.getPreco();
            model.addRow(new Object[]{
                    p.getId_produto(),
                    p.getNome(),
                    p.getCategoria().getNome_categoria(),
                    p.getQuantidade(),
                    p.getPreco(),
                    totalItem
            });
            totalGeral += totalItem;
        }
        TF_Total.setText(String.format("%.2f", totalGeral));
    }

    private void carregarCategorias() {
        CategoriaDAO dao = new CategoriaDAO();
        List<Categoria> lista = dao.listarTodos();
        comboCategoria.removeAllItems();
        for (Categoria c : lista) {
            comboCategoria.addItem(c);
        }
    }
}