/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jms_producer;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author fvasq
 */
public class Ventana extends JFrame {
    
    private Producer p = new Producer();
    
    private JLabel label;
    private JTextField texto;
    private List<JButton> botones;
    private JPanel panelPrincipal;
    
    public Ventana(String titulo, int ancho, int alto) {
        super(titulo);
        this.setSize(ancho, alto);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.iniciaComponentes();
        this.setVisible(true);
    }
    
    public void iniciaComponentes() {
        this.label = new JLabel("Escriba un mensaje");
        this.texto = new JTextField();
        this.botones = new ArrayList<JButton>();
        this.botones.add(new JButton("Enviar"));
        this.botones.add(new JButton("Conectar"));
        this.botones.add(new JButton("Desconectar"));
        
        LayoutManager disenioPrincipal = new BorderLayout();
        this.panelPrincipal = new JPanel(disenioPrincipal);
        LayoutManager disenioSup = new GridLayout(3, 2);
        JPanel panelSup = new JPanel(disenioSup);
        panelSup.add(this.botones.get(1));
        panelSup.add(this.botones.get(2));
        panelSup.add(this.label);
        panelSup.add(this.texto);
        panelSup.add(this.botones.get(0));
        
        this.panelPrincipal.add(panelSup, BorderLayout.NORTH);
        
        this.botones.get(0).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!texto.getText().isEmpty()) {
                    p.enviarMensaje(texto.getText());
                    texto.setText("");
                }
            }
        });
        
        this.botones.get(1).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                p.conectar();
            }
        });
        
        this.botones.get(2).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                p.desconectar();
            }
        });
        
        this.add(this.panelPrincipal);
        
    }
    
    public JLabel getLabel() {
        return label;
    }
    
    public void setLabel(JLabel label) {
        this.label = label;
    }
    
    public JTextField getTexto() {
        return texto;
    }
    
    public void setTexto(JTextField texto) {
        this.texto = texto;
    }
    
    public List<JButton> getBotones() {
        return botones;
    }
    
    public void setBotones(List<JButton> botones) {
        this.botones = botones;
    }
    
    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }
    
    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }
    
}
