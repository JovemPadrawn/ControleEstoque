import DAO.CategoriaDAO;
import Modelo.Categoria;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormGerenciadorCategoria extends JFrame {
    private JPanel Principal;
    private JTable table1;
    private JButton adicionarButton;
    private JButton excluirButton;
    private JButton salvarButton;
    private JButton editarButton;
    private JButton cancelarButton;
    private JTextField TF_nome_categoria;
    private JLabel lba_descricao;

    CategoriaDAO categoriaDAO = new CategoriaDAO();
    static int id;

    String[] colunas = {"ID", "CATEGORIA"};
    DefaultTableModel model = new DefaultTableModel(colunas, 0);

    public FormGerenciadorCategoria() {
        setContentPane(Principal);
        table1.setModel(model);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(500, 600);
        setTitle("Gerenciador de Categorias");
        setLocationRelativeTo(null);

        habilitarCampos(false);
        listarCategorias();

        adicionarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                habilitarCampos(true);
                salvarButton.setText("Salvar");
                TF_nome_categoria.requestFocus();
            }
        });

        salvarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome_categoria = TF_nome_categoria.getText().trim();

                if(nome_categoria.isEmpty()){
                    JOptionPane.showMessageDialog(null, "O nome da Categoria não pode ser vazio", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Categoria categoria = new Categoria(nome_categoria);

                if(salvarButton.getText().equals("Salvar")) {
                    if (categoriaDAO.inserir(categoria)) {
                        JOptionPane.showMessageDialog(null, "Categoria Inserida com Sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Erro ao inserir categoria", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    categoria.setId_categoria(id);
                    if (categoriaDAO.atualizar(categoria)) {
                        JOptionPane.showMessageDialog(null, "Categoria Atualizada com Sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Erro ao atualizar categoria", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
                habilitarCampos(false);
                listarCategorias();
            }
        });

        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int linhaSelecionada = table1.getSelectedRow();
                if (linhaSelecionada != -1) {
                    habilitarCampos(true);
                    id = Integer.parseInt(table1.getValueAt(linhaSelecionada, 0).toString());
                    TF_nome_categoria.setText(table1.getValueAt(linhaSelecionada, 1).toString());
                    salvarButton.setText("Atualizar");
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione uma linha para editar", "Aviso", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        excluirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int linhaSelecionada = table1.getSelectedRow();
                if (linhaSelecionada == -1) {
                    JOptionPane.showMessageDialog(null, "Selecione uma linha para excluir", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int idExcluir = Integer.parseInt(table1.getValueAt(linhaSelecionada, 0).toString());

                int resposta = JOptionPane.showConfirmDialog(null, "Deseja realmente excluir?", "Confirmação", JOptionPane.YES_NO_OPTION);
                if (resposta == JOptionPane.YES_OPTION) {
                    if (categoriaDAO.excluir(idExcluir)) {
                        JOptionPane.showMessageDialog(null, "Categoria excluída!");
                        listarCategorias();
                    } else {
                        JOptionPane.showMessageDialog(null, "Erro ao excluir (Verifique se há produtos vinculados).");
                    }
                }
            }
        });

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                habilitarCampos(false);
            }
        });
    }

    public void habilitarCampos(Boolean status){
        TF_nome_categoria.setEnabled(status);
        TF_nome_categoria.setText("");

        salvarButton.setEnabled(status);
        cancelarButton.setEnabled(status);

        adicionarButton.setEnabled(!status);
        editarButton.setEnabled(!status);
        excluirButton.setEnabled(!status);
    }

    public void listarCategorias(){
        model.setRowCount(0);
        for(Categoria cat : categoriaDAO.listarTodos()) {
            model.addRow(new Object[]{cat.getId_categoria(), cat.getNome_categoria()});
        }
    }
}
