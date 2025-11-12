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

    //CRIAMOS UM ARRAY DE STRING PARA O RÓTULO DA TABELA
    String[] colunas = {"ID", "CATEGORIA"};

    //INSERIRINDO O RÓTULO NA LINHA 0 DO OBJETO model
    DefaultTableModel model = new DefaultTableModel(colunas, 0);

    public FormGerenciadorCategoria() {

        setContentPane(Principal);
        table1.setModel(model);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 600);
        setTitle("Gerenciador de Categorias");
        setLocationRelativeTo(null);
        setVisible(true);

        habilitarCampos(false);
        limparCampos();
        listarCategorias();

        adicionarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                habilitarCampos(true);
            }
        });
        salvarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome_categoria = TF_nome_categoria.getText().trim();

                if(nome_categoria.isEmpty()){
                    JOptionPane.showMessageDialog(null,
                            "O nome da Categoria não pode ser vazio", "Erro", JOptionPane.ERROR_MESSAGE);
                    TF_nome_categoria.requestFocus();
                    return;
                }

                Categoria categoria = new Categoria(nome_categoria);

                if(salvarButton.getText().equals("Salvar")) {
                    if (categoriaDAO.inserir(categoria)) {
                        JOptionPane.showMessageDialog(null,
                                "Categoria Inserido com Sucesso!!!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Erro ao inserir o categoria no Banco de Dados", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }else{
                    //atualizar
                    categoria.setId_categoria(id);
                    if (categoriaDAO.atualizar(categoria)) {
                        JOptionPane.showMessageDialog(null,
                                "Categoria Atualizado com Sucesso!!!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Erro ao atualizar a categoria no Banco de Dados", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                    salvarButton.setText("Salvar");
                }
                //limparCampos();
                habilitarCampos(false);
                //adicionarButton.setEnabled(true);
                listarCategorias();
            }
        });
        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int linhaSelecionada = table1.getSelectedRow();

                excluirButton.setEnabled(true);

                if (linhaSelecionada != -1) {
                    // Preenche os campos com os dados da linha selecionada
                    habilitarCampos(true);

                    TF_nome_categoria.setText(table1.getValueAt(linhaSelecionada, 1).toString());

                    id = Integer.parseInt(table1.getValueAt(linhaSelecionada, 0).toString());
                    salvarButton.setText("Atualizar");
                    adicionarButton.setEnabled(false);
                }else{
                    adicionarButton.setEnabled(true);
                    excluirButton.setEnabled(false);
                    table1.clearSelection();
                    linhaSelecionada = -1;
                    JOptionPane.showMessageDialog(null,
                            "É necessário marcar uma linha para edição", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limparCampos();
                habilitarCampos(false);
            }
        });
        excluirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int resposta = JOptionPane.showConfirmDialog(
                        null,
                        "Realmente você deseja excluir? Não há retorno!!!",
                        "Confirmação para excluir o produto",
                        JOptionPane.YES_NO_OPTION
                );
                if (resposta == JOptionPane.YES_OPTION) {
                    if (categoriaDAO.excluir(id)) {
                        JOptionPane.showMessageDialog(null,
                                "Categoria excluida com Sucesso!!!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Erro ao excluir a categoria no Banco de Dados", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Você cancelou a operação.");
                }
                listarCategorias();
            }
        });
    }
    public void habilitarCampos(Boolean status){
        limparCampos();
        TF_nome_categoria.setEnabled(status);
        salvarButton.setEnabled(status);
        cancelarButton.setEnabled(status);
        editarButton.setEnabled(!status);
        adicionarButton.setEnabled(!status);
        excluirButton.setEnabled(!status);

    }
    public void limparCampos(){
        TF_nome_categoria.setText("");
    }

    public void listarCategorias(){
        model.setRowCount(0);
        double total = 0;
        for(Categoria cat : categoriaDAO.listarTodos()) {
            Object[] linha = {
                    cat.getId_categoria(),
                    cat.getNome_categoria()
            };
            model.addRow(linha);
        }
    }
}
