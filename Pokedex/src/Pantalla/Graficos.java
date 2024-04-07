package Pantalla;

import java.awt.EventQueue;
import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.SwingConstants;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.border.BevelBorder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.border.LineBorder;

public class Graficos extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;
    private final String rutaArchivo = "C:\\Users\\58414\\3D Objects\\Nueva carpeta\\OneDrive\\Documentos\\NetBeansProjects\\Pokedex\\src\\archivos\\";

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Graficos frame = new Graficos();
                    frame.setVisible(true);
                    frame.setLocationRelativeTo(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public Graficos() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 700, 550);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(240, 255, 255));
        panel.setBounds(0, 0, 700, 511);
        contentPane.add(panel);
        panel.setLayout(null);

        JPanel panel_1 = new JPanel();
        panel_1.setBackground(new Color(240, 255, 255));
        panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        panel_1.setBounds(100, 210, 471, 240);
        panel.add(panel_1);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(100, 75, 370, 100);
        panel.add(scrollPane);

        table = new JTable() {
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        table.setModel(new DefaultTableModel(
                new Object[][] {},
                new String[] { "Numero", "Nombre", "Tipo 1", "Tipo 2", "Sexo", "Fuerza" }
        ));
        scrollPane.setViewportView(table);

        JButton btnNewButton = new JButton("Gráficar");
        btnNewButton.setBackground(new Color(240, 255, 255));
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		DefaultTableModel modelo = (DefaultTableModel) table.getModel();
                DefaultCategoryDataset dataset = new DefaultCategoryDataset();

                // Obtener los datos de fuerza de los Pokémon
                for (int i = 0; i < modelo.getRowCount(); i++) {
                    String nombrePokemon = modelo.getValueAt(i, 1).toString();
                    int fuerzaPokemon = Integer.parseInt(modelo.getValueAt(i, 5).toString());
                    dataset.addValue(fuerzaPokemon, "Fuerza", nombrePokemon);
                }

                // Crear el gráfico de barras en 3D
                JFreeChart chart = ChartFactory.createBarChart3D(
                        "Fuerza de los Pokémon", // Título del gráfico
                        "Pokémon", // Etiqueta del eje X
                        "Fuerza", // Etiqueta del eje Y
                        dataset, // Conjunto de datos
                        PlotOrientation.VERTICAL,
                        true,
                        true,
                        false
                );

                // Crear el panel de gráfico y agregarlo al JPanel existente
                ChartPanel panelGrafico = new ChartPanel(chart);
                panelGrafico.setMouseWheelEnabled(true);
                panelGrafico.setPreferredSize(new Dimension(400,200));

                panel_1.removeAll(); // Eliminar cualquier componente anterior en el panel
                panel_1.setLayout(new BorderLayout());
                panel_1.add(panelGrafico, BorderLayout.CENTER);
                panel_1.revalidate(); // Actualizar la interfaz gráfica
                       
            
        	}
        });
        btnNewButton.setBounds(480, 135, 89, 40);
        panel.add(btnNewButton);

        JButton btnNewButton_1 = new JButton("Seleccionar archivo");
        btnNewButton_1.setBackground(new Color(240, 255, 255));
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	JFileChooser fileChooser = new JFileChooser();
			    fileChooser.setCurrentDirectory(new File(rutaArchivo));
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos CSV", "csv");
                fileChooser.setFileFilter(filter);
                int result = fileChooser.showOpenDialog(Graficos.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File archivo = fileChooser.getSelectedFile();
                    DefaultTableModel modelo = (DefaultTableModel) table.getModel();
                    try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                        modelo.setRowCount(0); // Limpiar la tabla antes de cargar nuevos datos
                        String linea;
                        while ((linea = br.readLine()) != null) {
                            String[] datos = linea.split(",");
                            modelo.addRow(datos);
                        }
                        JOptionPane.showMessageDialog(Graficos.this, "Datos cargados correctamente.");
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(Graficos.this, "Error al cargar los datos.");
                        ex.printStackTrace();
                    }
                }
            }
        });
        btnNewButton_1.setBounds(480, 75, 89, 40);
        panel.add(btnNewButton_1);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBounds(0, 0, 732, 22);
        panel.add(menuBar);

        JMenuItem mntmNewMenuItem = new JMenuItem("Atrás");
        mntmNewMenuItem.setBackground(new Color(240, 255, 255));
        mntmNewMenuItem.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		FrameInicio in = new FrameInicio();
        		in.frmPokedex.setVisible(true);
        		in.frmPokedex.setLocationRelativeTo(null);
        		dispose();
        	}
        });
        menuBar.add(mntmNewMenuItem);
        
        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(new ImageIcon(Graficos.class.getResource("/Imagenes/dgx9c0b-a151db78-1b0a-47dd-b2cc-5dd523fff864 (2).png")));
        lblNewLabel.setBounds(-10, 22, 700, 489);
        panel.add(lblNewLabel);
    }
}
