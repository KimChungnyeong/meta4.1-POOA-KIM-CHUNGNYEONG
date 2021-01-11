package gui;

import kanvan.Actividad;
import kanvan.Fase;
import kanvan.FlujoTrabajo;
import kanvan.Tarea;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ClienteTablero extends JDialog {
    private JPanel contentPane;
    private JButton buttonAddActividad;
    private JTextField textFieldActividad;
    private JComboBox comboBoxActividad;
    private JTable tablero;
    private JButton buttonSalir;
    private JComboBox comboBoxFase;
    private JTextField textFieldTarea;
    private JComboBox comboBoxTarea;
    private JButton botonAddTarea;
    private JButton chatButton;
    private JButton botonAddActividad;
    private JButton buttonOK;
    private JButton buttonCancel;
    private FlujoTrabajo flujoTrabajo;
    private DefaultTableModel modelo;
    public ClienteTablero() {
        setContentPane(contentPane);
        setPreferredSize(new Dimension(500,400));
        setResizable(false);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        flujoTrabajo=new FlujoTrabajo("Flujo de Trabajo");

        Fase fase = new Fase("PENDIENTE",flujoTrabajo);
        Fase fase1 = new Fase("EN CURSO",flujoTrabajo);
        Fase fase2 = new Fase("TERMINADO",flujoTrabajo);
        flujoTrabajo.getFase().add(fase);
        flujoTrabajo.getFase().add(fase1);
        flujoTrabajo.getFase().add(fase2);

        botonAddActividad.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Actividad actividad = new Actividad(textFieldActividad.getText(),flujoTrabajo);
                flujoTrabajo.getActividad().add(actividad);
                actualizarTablero();
                grabar();
            }
        });
        botonAddTarea.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Actividad actividad = flujoTrabajo.getActividad().get(comboBoxActividad.getSelectedIndex());
                Fase fase = flujoTrabajo.getFase().get(comboBoxFase.getSelectedIndex());

                Tarea tarea = new Tarea(textFieldTarea.getText(),flujoTrabajo,actividad,fase);
                actividad.getTarea().add(tarea);
                fase.getTarea().add(tarea);
                flujoTrabajo.getTarea().add(tarea);
                actualizarTablero();
                grabar();
            }
        });
    }
    private void grabar()
    {
        try {
            FileOutputStream fileout = new FileOutputStream("Trabajo.dat");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileout);
            objectOutputStream.writeObject(flujoTrabajo);
            objectOutputStream.close();
            fileout.close();
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private void  actualizarTablero() {
        modelo=new DefaultTableModel();;
        tablero.setModel(modelo);

        comboBoxActividad.removeAllItems();
        for(int i=0;i<flujoTrabajo.getActividad().size();i++)
        {
            comboBoxActividad.addItem(flujoTrabajo.getActividad().get(i).getNombre());
        }

        comboBoxFase.removeAllItems();
        for(int i=0;i<flujoTrabajo.getFase().size();i++)
        {
            comboBoxFase.addItem(flujoTrabajo.getFase().get(i).getNombre());
        }

        comboBoxTarea.removeAllItems();
        for (int j = 0; j < flujoTrabajo.getTarea().size(); j++) {
            comboBoxTarea.addItem(flujoTrabajo.getTarea().get(j).getNombre());
        }

        for(int i=0;i<flujoTrabajo.getFase().size();i++)
        {
            modelo.addColumn(flujoTrabajo.getFase().get(i).getNombre(),flujoTrabajo.getFase().get(i).getTarea());
        }
    }

    public static void main(String[] args) {
        ClienteTablero dialog = new ClienteTablero();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
