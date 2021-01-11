package gui;

import kanvan.Actividad;
import kanvan.Fase;
import kanvan.FlujoTrabajo;
import kanvan.Tarea;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Tablero extends JDialog {
    kanvan.FlujoTrabajo flujoTrabajo;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JButton botonAddActividad;
    private JButton botonAddTarea;
    private JTextField textFieldActividad;
    private JTextField textFieldTarea;
    private JComboBox comboBoxActividad;
    private JComboBox comboBoxFase;
    private JComboBox comboBoxTarea;
    private JTable tablero;
    private JButton buttonCarga;
    private JButton buttonBorraFila;
    private JButton salirButton;
    //private JButton agregarTareaButton;
    private DefaultTableModel modelo;

    public Tablero() {

        flujoTrabajo=new FlujoTrabajo("flujo de trabajo");
        setContentPane(contentPane);
        setPreferredSize(new Dimension(500,400));
        setResizable(false);
        setModal(true);
        comboBoxTarea.setVisible(false);
        getRootPane().setDefaultButton(buttonOK);


        Fase fase = new Fase("PENDIENTE",flujoTrabajo);
        Fase fase1 = new Fase("EN CURSO",flujoTrabajo);
        Fase fase2 = new Fase("TERMINADO",flujoTrabajo);
        flujoTrabajo.getFase().add(fase);
        flujoTrabajo.getFase().add(fase1);
        flujoTrabajo.getFase().add(fase2);

        actualizarTablero();
        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        botonAddActividad.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Actividad actividad = new Actividad(textFieldActividad.getText(),flujoTrabajo);
                flujoTrabajo.getActividad().add(actividad);
                grabar();
                actualizarTablero();

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
                grabar();
                actualizarTablero();
                //JOptionPane.showMessageDialog(null,flujoTrabajo);
            }
        });
        buttonCarga.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    FileInputStream fileinput = new FileInputStream("Trabajo.dat");
                    ObjectInputStream objectInputStream = new ObjectInputStream(fileinput);

                    flujoTrabajo = (FlujoTrabajo) objectInputStream.readObject();

                    objectInputStream.close();
                    fileinput.close();

                }catch(FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                }
                actualizarTablero();
            }
        });
        buttonBorraFila.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int fila,ban=0;
                try {
                    fila= tablero.getSelectedRow();
                    if(fila==-1)
                    {
                        JOptionPane.showMessageDialog(null,"Elegir una fila a borrar");
                    }
                    else
                        ban=JOptionPane.showConfirmDialog(null,"Esta seguro que lo quiere borrar?");
                        if(ban==JOptionPane.YES_OPTION)
                        {
                            Fase fase = flujoTrabajo.getTarea().get(comboBoxActividad.getSelectedIndex()).getFase();
                            Actividad actividad = flujoTrabajo.getTarea().get(comboBoxActividad.getSelectedIndex()).getActividad();
                            Tarea tarea = flujoTrabajo.getTarea().get(comboBoxActividad.getSelectedIndex());

                            flujoTrabajo.getTarea().removeElement(tarea);
                            fase.getTarea().removeElement(tarea);
                            actividad.getTarea().remove(tarea);
                            grabar();
                            actualizarTablero();
                        }
                }catch(Exception f){
                }
            }
        });
        salirButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
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

    /*private void recuperar()
    {
        try {
            FileInputStream fileinput = new FileInputStream("Trabajo.dat");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileinput);

            flujoTrabajo = (FlujoTrabajo) objectInputStream.readObject();

            objectInputStream.close();
            fileinput.close();

        }catch(FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (ClassNotFoundException classNotFoundException) {
            classNotFoundException.printStackTrace();
        }

        actualizarTablero();
    }*/

    private void onCancel() {
        // add your code here if necessary
        dispose();
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

    public static void main(String [] args)
    {
        Tablero dialog = new Tablero();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
