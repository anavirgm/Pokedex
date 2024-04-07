package Pantalla;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Font;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;
import java.awt.event.InputEvent;

public class FrameInicio {

	protected static final MouseEvent Mouse_evn = null;
	JFrame frmPokedex;
	private JTextField Numero;
	private JTextField Ubicacion;
	private JTextField Nombre;
	private File archivoSeleccionado;
	private File archivoDatos;
	private final String rutaArchivo = "C:\\Users\\58414\\3D Objects\\Nueva carpeta\\OneDrive\\Documentos\\NetBeansProjects\\Pokedex\\src\\archivos\\";

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameInicio window = new FrameInicio();
					window.frmPokedex.setVisible(true);
					window.frmPokedex.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	DefaultTableModel Modelo = new DefaultTableModel();
	private JTable Tabla_1;
	
	private ArrayList<Pokemon> Pokedex;
	private JLabel lblAdvertencia_Tipo1;
	private JLabel lblAdvertencia_Sexo;

	Pokemon pokemon = new Pokemon();
	
	public FrameInicio() {
		Pokedex = new ArrayList<Pokemon>();
		String [] Titulo = new String [] {"Numero" , "Nombre" , "Tipo 1" , "Tipo 2" , "Sexo" , "Fuerza" };
		Modelo.setColumnIdentifiers(Titulo);
 		initialize();
 		Tabla_1.setModel(Modelo);
 		
 		JLabel lblNewLabel_1 = new JLabel("New label");
 		lblNewLabel_1.setIcon(new ImageIcon(FrameInicio.class.getResource("/Imagenes/pokedisenio.png")));
 		lblNewLabel_1.setBounds(0, 0, 449, 614);
 		frmPokedex.getContentPane().add(lblNewLabel_1);
 		
 		
 		
 		cargarDatosEnTabla();
		}
	
	
	
	private void cargarDatosEnTabla() {
        DefaultTableModel Modelo = (DefaultTableModel) Tabla_1.getModel();
        for (Pokemon pokemon : Pokedex) {
            Modelo.addRow(new Object[] {
                pokemon.getNumero(),
                pokemon.getNombre(),
                pokemon.getTipo1(),
                pokemon.getTipo2(),
                pokemon.getSexo(),
                pokemon.getUbicacion()
            });
        }
    }
	
	private void guardarDatos() {
		JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(rutaArchivo));

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos CSV", "csv");
        fileChooser.setFileFilter(filter);

        int result = fileChooser.showSaveDialog(frmPokedex);
        if (result == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            // Agregar la extensión .csv si no está presente
            if (!archivo.getName().toLowerCase().endsWith(".csv")) {
                archivo = new File(archivo.getAbsolutePath() + ".csv");
            }

            try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {
                for (int i = 0; i < Modelo.getRowCount(); i++) {
                    StringBuilder sb = new StringBuilder();
                    for (int j = 0; j < Modelo.getColumnCount(); j++) {
                        if (j > 0) {
                            sb.append(",");
                        }
                        sb.append(Modelo.getValueAt(i, j));
                    }
                    pw.println(sb.toString());
                }
                JOptionPane.showMessageDialog(frmPokedex, "Datos guardados correctamente.");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(frmPokedex, "Error al guardar los datos.");
                e.printStackTrace();
            }
        }
    }

    private void abrirArchivo() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(rutaArchivo));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos CSV", "csv");
        fileChooser.setFileFilter(filter);
        int result = fileChooser.showOpenDialog(frmPokedex);
        if (result == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                Modelo.setRowCount(0); // Limpiar la tabla antes de cargar nuevos datos
                String linea;
                while ((linea = br.readLine()) != null) {
                    String[] datos = linea.split(",");
                    Modelo.addRow(datos);
                }
                JOptionPane.showMessageDialog(frmPokedex, "Datos cargados correctamente.");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(frmPokedex, "Error al cargar los datos.");
                e.printStackTrace();
            }
	    }
	}


	
	
	private void initialize() {
		frmPokedex = new JFrame();
		frmPokedex.setIconImage(Toolkit.getDefaultToolkit().getImage(FrameInicio.class.getResource("/Imagenes/pngwing.com.png")));
		frmPokedex.setBackground(new Color(205, 92, 92));
		frmPokedex.getContentPane().setBackground(new Color(230, 230, 250));
		frmPokedex.setTitle("Pokedex");
		frmPokedex.setBounds(100, 100, 465, 676);
		frmPokedex.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBorder(null);
		menuBar.setBackground(new Color(240, 255, 255));
		frmPokedex.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Archivo");
		mnNewMenu.setFont(new Font("Tahoma", Font.PLAIN, 12));
		mnNewMenu.setForeground(new Color(0, 0, 0));
		menuBar.add(mnNewMenu);
		
		
		//ABRIR ARCHIVO TXT
		JMenuItem mntmNewMenuItem = new JMenuItem("Abrir ");
		mntmNewMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirArchivo();
				
			}
		});
		mntmNewMenuItem.setFont(new Font("Tahoma", Font.PLAIN, 13));
		mntmNewMenuItem.setIcon(new ImageIcon(FrameInicio.class.getResource("/Imagenes/icons8-abrir-archivo-48 (1).png")));
		mnNewMenu.add(mntmNewMenuItem);
		
		
		//GUARDAR DATOS DE JTABLE EN ARCHIVO TXT
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Guardar");
		mntmNewMenuItem_1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
		mntmNewMenuItem_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		mntmNewMenuItem_1.setIcon(new ImageIcon(FrameInicio.class.getResource("/Imagenes/icons8-guardar-48 (1).png")));
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guardarDatos();
			}
		});
		mnNewMenu.add(mntmNewMenuItem_1);
		
		
		//CERRAR PROGRAMA
		JMenuItem Salir = new JMenuItem("Salir");
		Salir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));
		Salir.setFont(new Font("Tahoma", Font.PLAIN, 13));
		Salir.setIcon(new ImageIcon(FrameInicio.class.getResource("/Imagenes/icons8-salir-redondeado-100 (1).png")));
		Salir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnNewMenu.add(Salir);
		
		JMenu mnNewMenu_2 = new JMenu("Estadísticas");
		mnNewMenu_2.setForeground(Color.BLACK);
		mnNewMenu_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		menuBar.add(mnNewMenu_2);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Gráficos");
		mntmNewMenuItem_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		mntmNewMenuItem_2.setIcon(new ImageIcon(FrameInicio.class.getResource("/Imagenes/icons8-gráfico-de-barras-20.png")));
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Graficos gf = new Graficos();
				gf.setVisible(true);
				gf.setLocationRelativeTo(null);
			}
		});
		mnNewMenu_2.add(mntmNewMenuItem_2);
		
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("Generar reporte");
		mntmNewMenuItem_3.setFont(new Font("Tahoma", Font.PLAIN, 13));
		mntmNewMenuItem_3.setIcon(new ImageIcon(FrameInicio.class.getResource("/Imagenes/icons8-archivo-20.png")));
		mntmNewMenuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Document document = new Document();
		        try {
		            JFileChooser fileChooser = new JFileChooser();
		            fileChooser.setDialogTitle("Guardar reporte");
		            fileChooser.setCurrentDirectory(new File(rutaArchivo));
		            int userSelection = fileChooser.showSaveDialog(frmPokedex);
		            if (userSelection == JFileChooser.APPROVE_OPTION) {
		                File fileToSave = fileChooser.getSelectedFile();
		                String filePath = fileToSave.getAbsolutePath();
		                if (!filePath.endsWith(".pdf")) {
		                    filePath += ".pdf";
		                }
		                PdfWriter.getInstance(document, new FileOutputStream(filePath));
		                document.open();
		                for (int i = 0; i < Modelo.getRowCount(); i++) {
		                    for (int j = 0; j < Modelo.getColumnCount(); j++) {
		                        Object value = Modelo.getValueAt(i, j);
		                        if (value != null) {
		                            document.add(new Paragraph(value.toString()));
		                        } else {
		                            document.add(new Paragraph(""));
		                        }
		                    }
		                    document.add(new Paragraph("\n"));
		                }

		                document.close();
		                JOptionPane.showMessageDialog(frmPokedex, "Reporte generado exitosamente.");
		            }
		        } catch (DocumentException | IOException e1) {
		            e1.printStackTrace();
		            JOptionPane.showMessageDialog(frmPokedex, "Error al generar el reporte.");
		        }
			}
		});
		mnNewMenu_2.add(mntmNewMenuItem_3);
		
		JMenuItem mntmNewMenuItem_5 = new JMenuItem("Abrir reporte");
		mntmNewMenuItem_5.setFont(new Font("Tahoma", Font.PLAIN, 13));
		mntmNewMenuItem_5.setIcon(new ImageIcon(FrameInicio.class.getResource("/Imagenes/icons8-pdf-20.png")));
		mntmNewMenuItem_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 JFileChooser fileChooser = new JFileChooser();
				    fileChooser.setCurrentDirectory(new File(rutaArchivo));
				    FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos PDF", "pdf");
				    fileChooser.setFileFilter(filter);
				    int result = fileChooser.showOpenDialog(frmPokedex);
				    if (result == JFileChooser.APPROVE_OPTION) {
				        File selectedFile = fileChooser.getSelectedFile();
				        try {
				            Desktop.getDesktop().open(selectedFile);
				        } catch (IOException ex) {
				            ex.printStackTrace();
				            JOptionPane.showMessageDialog(frmPokedex, "Error al abrir el archivo PDF.");
				        }
		        }
			}
		});
		mnNewMenu_2.add(mntmNewMenuItem_5);
		
		JMenu mnNewMenu_1 = new JMenu("Ayuda");
		mnNewMenu_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		mnNewMenu_1.setForeground(new Color(0, 0, 0));
		menuBar.add(mnNewMenu_1);
		
		
		//INFORMACION DEL PROGRAMADOR
		JMenuItem Acerca = new JMenuItem("Acerca de");
		Acerca.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));
		Acerca.setIcon(new ImageIcon(FrameInicio.class.getResource("/Imagenes/icons8-información-48 (2).png")));
		Acerca.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        JOptionPane.showMessageDialog(frmPokedex, "<html><div style='text-align: center;'>" +
		                "<h3>Programador:</h3>" +
		                "<p>ANA V. MOTA</p>" +
		                "<p>Ingeniería en Informática</p>" +
                                "<p>♡</p>" +
                                 "<p> </p>" +
		                "</div></html>");
		    }
		});
		mnNewMenu_1.add(Acerca);

		frmPokedex.getContentPane().setLayout(null);
		
		Numero = new JTextField();
		Numero.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
			}
		});
		Numero.setForeground(Color.BLACK);
		Numero.setBounds(31, 133, 172, 31);
		frmPokedex.getContentPane().add(Numero);
		Numero.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Numero");
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel.setBounds(31, 108, 66, 14);
		frmPokedex.getContentPane().add(lblNewLabel);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setForeground(Color.BLACK);
		lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNombre.setBounds(246, 108, 66, 14);
		frmPokedex.getContentPane().add(lblNombre);
		
		JLabel lblTipo = new JLabel("Tipo 1");
		lblTipo.setForeground(Color.BLACK);
		lblTipo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblTipo.setBounds(31, 175, 66, 14);
		frmPokedex.getContentPane().add(lblTipo);
		
		JLabel lblTipo_1 = new JLabel("Tipo 2");
		lblTipo_1.setForeground(Color.BLACK);
		lblTipo_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblTipo_1.setBounds(246, 175, 66, 14);
		frmPokedex.getContentPane().add(lblTipo_1);
		
		JComboBox Tipo1 = new JComboBox();
		Tipo1.setForeground(Color.BLACK);
		Tipo1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
			}
		});
		Tipo1.setToolTipText("");
		Tipo1.setModel(new DefaultComboBoxModel(new String[] {"", "Normal", "Acero", "Agua", "Bicho", "Dragón", "Eléctrico", "Fuego", "Fantasma", "Hielo", "Hada", "Lucha", "Planta", "Psíquico", "Roca", "Tierra", "Volador", "Veneno"}));
		Tipo1.setBounds(31, 200, 172, 31);
		frmPokedex.getContentPane().add(Tipo1);
		
		JComboBox Tipo2 = new JComboBox();
		Tipo2.setForeground(Color.BLACK);
		Tipo2.setModel(new DefaultComboBoxModel(new String[] {"", "Normal", "Acero", "Agua", "Bicho", "Dragón", "Eléctrico", "Fuego", "Fantasma", "Hielo", "Hada", "Lucha", "Planta", "Psíquico", "Roca", "Tierra", "Volador", "Veneno"}));
		Tipo2.setBounds(246, 200, 172, 31);
		frmPokedex.getContentPane().add(Tipo2);
		
		JComboBox Sexo = new JComboBox();
		Sexo.setForeground(Color.BLACK);
		Sexo.setModel(new DefaultComboBoxModel(new String[] {"", "Femenino", "Masculino", "Ninguno"}));
		Sexo.setBounds(31, 267, 172, 31);
		frmPokedex.getContentPane().add(Sexo);
		
		Ubicacion = new JTextField();
		Ubicacion.setForeground(Color.BLACK);
		Ubicacion.setColumns(10);
		Ubicacion.setBounds(246, 267, 172, 31);
		frmPokedex.getContentPane().add(Ubicacion);
		
		Nombre = new JTextField();
		Nombre.setForeground(Color.BLACK);
		Nombre.setColumns(10);
		Nombre.setBounds(246, 133, 172, 31);
		frmPokedex.getContentPane().add(Nombre);
		
		JLabel lblSexo = new JLabel("Sexo");
		lblSexo.setForeground(Color.BLACK);
		lblSexo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblSexo.setBounds(31, 242, 66, 14);
		frmPokedex.getContentPane().add(lblSexo);
		
		JLabel lblUbicacin = new JLabel("Fuerza");
		lblUbicacin.setForeground(Color.BLACK);
		lblUbicacin.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblUbicacin.setBounds(246, 242, 66, 14);
		frmPokedex.getContentPane().add(lblUbicacin);
		
		
		//LIMPIAR LOS CAMPOS
		JButton btnNewButton = new JButton("Limpiar");
		btnNewButton.setForeground(Color.BLACK);
		btnNewButton.setBackground(new Color(153, 204, 255));
		btnNewButton.setIcon(new ImageIcon(FrameInicio.class.getResource("/Imagenes/icons8-servicio-de-limpieza-16.png")));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Numero.setText(null);
				Nombre.setText(null);
				Tipo1.setSelectedIndex(0);
				Tipo2.setSelectedIndex(0);
				Sexo.setSelectedIndex(0);
				Ubicacion.setText(null);
				
			}
		});
		
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnNewButton.setBounds(162, 320, 125, 36);
		frmPokedex.getContentPane().add(btnNewButton);
		
		
		//ACTUALIZAR
		JButton btnActualizar = new JButton("Actualizar");
		btnActualizar.setForeground(Color.BLACK);
		btnActualizar.setBackground(new Color(153, 204, 255));
		btnActualizar.setIcon(new ImageIcon(FrameInicio.class.getResource("/Imagenes/icons8-actualizar-16.png")));
		btnActualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				DefaultTableModel Modelo = (DefaultTableModel) Tabla_1.getModel();
		        if (Numero.getText().isEmpty() || Nombre.getText().isEmpty() || Tipo1.getSelectedIndex() == 0 || Sexo.getSelectedIndex() == 0 || Ubicacion.getText().isEmpty()) {
		            JOptionPane.showMessageDialog(null, "Número, Nombre, Tipo 1, Sexo y Ubicación son requeridos.");
		        } else {
		            try {
		                String Nro = Numero.getText();
		                String Name = Nombre.getText();
		                String Tipe1 = (String) Tipo1.getSelectedItem();
		                String Tipe2= (String) Tipo2.getSelectedItem();
		                String Sex = (String) Sexo.getSelectedItem();
		                String Ubi = Ubicacion.getText();
		                Modelo.setValueAt(Nro, Tabla_1.getSelectedRow(), 0);
		                Modelo.setValueAt(Name, Tabla_1.getSelectedRow(), 1);
		                Modelo.setValueAt(Tipe1, Tabla_1.getSelectedRow(), 2);
		                Modelo.setValueAt(Tipe2, Tabla_1.getSelectedRow(), 3);
		                Modelo.setValueAt(Sex, Tabla_1.getSelectedRow(), 4);
		                Modelo.setValueAt(Ubi, Tabla_1.getSelectedRow(), 5);
		            } catch (Exception e1) {
		                JOptionPane.showMessageDialog(null, "Seleccione la fila que desea actualizar.");
		            }
				}
				
			}
		});
		btnActualizar.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnActualizar.setBounds(297, 320, 121, 36);
		frmPokedex.getContentPane().add(btnActualizar);
		
		
		//REGISTRO
		JButton btnRegistrar = new JButton("Registrar");
		btnRegistrar.setForeground(Color.BLACK);
		btnRegistrar.setBackground(new Color(153, 204, 255));
		btnRegistrar.setIcon(new ImageIcon(FrameInicio.class.getResource("/Imagenes/icons8-más-2-matemáticas-16.png")));
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (Numero.getText().isEmpty() || Nombre.getText().isEmpty() || Tipo1.getSelectedIndex() == 0 || Sexo.getSelectedIndex() == 0 || Ubicacion.getText().isEmpty()) {
		            JOptionPane.showMessageDialog(null, "Número, Nombre, Tipo 1, Sexo y Fuerza son requeridos.");
		        } else {
		            try {
		                DefaultTableModel modelo = (DefaultTableModel) Tabla_1.getModel();
		                modelo.addRow(new Object[] {
		                    Numero.getText(),
		                    Nombre.getText(),
		                    (String) Tipo1.getSelectedItem(),
		                    (String) Tipo2.getSelectedItem(),
		                    (String) Sexo.getSelectedItem(),
		                    Ubicacion.getText()
		                });
		                Tabla_1.repaint();
		                Numero.setText(null);
		                Nombre.setText(null);
		                Tipo1.setSelectedIndex(0);
		                Tipo2.setSelectedIndex(0);
		                Sexo.setSelectedIndex(0);
		                Ubicacion.setText(null);
		            } catch (Exception exc) {
		                JOptionPane.showMessageDialog(null, "Error al agregar el Pokémon. Verifica los datos.");
		            }
				}
			}
		});

		btnRegistrar.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnRegistrar.setBounds(31, 320, 121, 36);
		frmPokedex.getContentPane().add(btnRegistrar);
		
		
		//ELIMINAR FILA SELECCIONADA
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.setForeground(Color.BLACK);
		btnEliminar.setBackground(new Color(153, 204, 255));
		btnEliminar.setIcon(new ImageIcon(FrameInicio.class.getResource("/Imagenes/icons8-eliminar-16.png")));
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int deleteRow = Tabla_1.getSelectedRow();
					Modelo.removeRow(deleteRow);
				}catch(Exception e1){
					JOptionPane.showMessageDialog(null, "Seleccione una fila.");
				}
					
			}
		});
		btnEliminar.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnEliminar.setBounds(82, 505, 121, 36);
		frmPokedex.getContentPane().add(btnEliminar);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(31, 378, 387, 116);
		frmPokedex.getContentPane().add(scrollPane);
		
		
		
		Tabla_1 = new JTable() {
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		Tabla_1.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        DefaultTableModel modelo = (DefaultTableModel) Tabla_1.getModel();

		        int filaSeleccionada = Tabla_1.getSelectedRow();
		        if (filaSeleccionada != -1) {
		            String nro = obtenerValorCelda(modelo, filaSeleccionada, 0);
		            String name = obtenerValorCelda(modelo, filaSeleccionada, 1);
		            String tipe1 = obtenerValorCelda(modelo, filaSeleccionada, 2);
		            String tipe2 = obtenerValorCelda(modelo, filaSeleccionada, 3);
		            String sex = obtenerValorCelda(modelo, filaSeleccionada, 4);
		            String ubi = obtenerValorCelda(modelo, filaSeleccionada, 5);

		            Numero.setText(nro);
		            Nombre.setText(name);
		            Tipo1.setSelectedItem(tipe1);
		            Tipo2.setSelectedItem(tipe2);
		            Sexo.setSelectedItem(sex);
		            Ubicacion.setText(ubi);
		        }
		    }

		    private String obtenerValorCelda(DefaultTableModel modelo, int fila, int columna) {
		        Object valor = modelo.getValueAt(fila, columna);
		        return (valor != null) ? valor.toString() : "";
		    }
		});

		Tabla_1.setModel(new DefaultTableModel(
		    new Object[][] {},
		    new String[] { "Numero", "Nombre", "Tipo 1", "Tipo 2", "Sexo", "Fuerza" }
		));
		scrollPane.setViewportView(Tabla_1);

		
		lblAdvertencia_Tipo1 = new JLabel("");
		lblAdvertencia_Tipo1.setBounds(31, 156, 172, 19);
		frmPokedex.getContentPane().add(lblAdvertencia_Tipo1);
		
		lblAdvertencia_Sexo = new JLabel("");
		lblAdvertencia_Sexo.setForeground(Color.RED);
		lblAdvertencia_Sexo.setBounds(31, 247, 172, 19);
		frmPokedex.getContentPane().add(lblAdvertencia_Sexo);
		
		JButton btnEliminarTodo = new JButton("Eliminar Todo");
		btnEliminarTodo.setForeground(Color.BLACK);
 		btnEliminarTodo.addActionListener(new ActionListener() {
 			public void actionPerformed(ActionEvent e) {
 				int confirmacion = JOptionPane.showConfirmDialog(frmPokedex,
 		                "¿Está seguro de que desea eliminar todos los datos?",
 		                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
 		        if (confirmacion == JOptionPane.YES_OPTION) {
 		            Modelo.setRowCount(0);
 		            Numero.setText(null);
 		            Nombre.setText(null);
 		            Tipo1.setSelectedIndex(0);
 		            Tipo2.setSelectedIndex(0);
 		            Sexo.setSelectedIndex(0);
 		            Ubicacion.setText(null);
 		            JOptionPane.showMessageDialog(frmPokedex, "Tabla eliminada");
 		        }
 			}
 		});
 		btnEliminarTodo.setIcon(new ImageIcon(FrameInicio.class.getResource("/Imagenes/icons8-eliminar-propiedad-16.png")));
 		btnEliminarTodo.setFont(new Font("Tahoma", Font.PLAIN, 13));
 		btnEliminarTodo.setBackground(new Color(250, 128, 114));
 		btnEliminarTodo.setBounds(246, 505, 131, 36);
 		frmPokedex.getContentPane().add(btnEliminarTodo);
		
	}
}
