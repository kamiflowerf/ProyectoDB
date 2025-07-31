package visual;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.Date;
import logico.*;

public class ReporteEvento extends JDialog {
    private final JPanel contentPanel = new JPanel();
    private Evento evento;
    private JTable tableComisiones;
    private JTable tableRecursos;
    private DefaultTableModel modelComisiones;
    private DefaultTableModel modelRecursos;
    private JLabel lblTotalComisiones;
    private JLabel lblTotalRecursos;
    private JLabel lblTotalTrabajos;

    public static void main(String[] args) {
        try {
            Evento eventoEjemplo = new Evento("E-1", "Evento de Prueba", "Conferencia", new Date());
            ReporteEvento dialog = new ReporteEvento(eventoEjemplo);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ReporteEvento(Evento evento) {
        this.evento = evento;
        if (evento == null) {
            dispose();
            return;
        }

        setTitle("Reporte de Evento: " + evento.getTitulo());
        setBounds(100, 100, 800, 600);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPanel.setBackground(UIManager.getColor("InternalFrame.activeTitleGradient") != null ?
            UIManager.getColor("InternalFrame.activeTitleGradient") :
            new Color(240, 240, 240));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new BorderLayout(0, 0));

        // Panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(UIManager.getColor("InternalFrame.activeTitleBackground") != null ?
            UIManager.getColor("InternalFrame.activeTitleBackground") :
            new Color(240, 240, 240));
        mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPanel.add(mainPanel);
        mainPanel.setLayout(null);

        // Panel Información General
        JPanel infoPanel = new JPanel();
        infoPanel.setBorder(new TitledBorder(null, "Información General", 
            TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
        infoPanel.setBackground(UIManager.getColor("InternalFrame.activeTitleBackground") != null ?
            UIManager.getColor("InternalFrame.activeTitleBackground") :
            new Color(240, 240, 240));
        infoPanel.setBounds(10, 11, 764, 100);
        mainPanel.add(infoPanel);
        infoPanel.setLayout(null);

        // Detalles del evento
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        JLabel lblId = new JLabel("ID: " + evento.getId());
        lblId.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblId.setBounds(10, 20, 200, 14);
        infoPanel.add(lblId);

        JLabel lblFecha = new JLabel("Fecha: " + sdf.format(evento.getFecha()));
        lblFecha.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblFecha.setBounds(10, 40, 200, 14);
        infoPanel.add(lblFecha);

        JLabel lblTipo = new JLabel("Tipo: " + evento.getTipo());
        lblTipo.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblTipo.setBounds(10, 60, 200, 14);
        infoPanel.add(lblTipo);

        // Panel de Estadísticas
        JPanel statsPanel = new JPanel();
        statsPanel.setBorder(new TitledBorder(null, "Estadísticas", 
            TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
        statsPanel.setBackground(UIManager.getColor("InternalFrame.activeTitleBackground") != null ?
            UIManager.getColor("InternalFrame.activeTitleBackground") :
            new Color(240, 240, 240));
        statsPanel.setBounds(10, 122, 764, 80);
        mainPanel.add(statsPanel);
        statsPanel.setLayout(null);

        lblTotalComisiones = new JLabel("Total Comisiones: " + evento.getComisiones().size());
        lblTotalComisiones.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblTotalComisiones.setBounds(10, 20, 200, 14);
        statsPanel.add(lblTotalComisiones);

        lblTotalRecursos = new JLabel("Total Recursos: " + evento.getRecursos().size());
        lblTotalRecursos.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblTotalRecursos.setBounds(10, 40, 200, 14);
        statsPanel.add(lblTotalRecursos);

        int totalTrabajos = contarTrabajosTotal();
        lblTotalTrabajos = new JLabel("Total Trabajos: " + totalTrabajos);
        lblTotalTrabajos.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblTotalTrabajos.setBounds(220, 20, 200, 14);
        statsPanel.add(lblTotalTrabajos);

        // Panel de pestañas
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(10, 213, 764, 300);
        mainPanel.add(tabbedPane);

        // Primera pestaña: Comisiones
        JPanel comisionesPanel = new JPanel(new BorderLayout());
        comisionesPanel.setBackground(UIManager.getColor("InternalFrame.activeTitleBackground") != null ?
            UIManager.getColor("InternalFrame.activeTitleBackground") :
            new Color(240, 240, 240));
        comisionesPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        tabbedPane.addTab("Comisiones", null, comisionesPanel, null);

        modelComisiones = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        String[] headerComisiones = {"Código", "Nombre", "Área", "Jurados", "Trabajos"};
        modelComisiones.setColumnIdentifiers(headerComisiones);

        tableComisiones = new JTable(modelComisiones);
        tableComisiones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollComisiones = new JScrollPane(tableComisiones);
        scrollComisiones.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        comisionesPanel.add(scrollComisiones);

        // Segunda pestaña: Recursos
        JPanel recursosPanel = new JPanel(new BorderLayout());
        recursosPanel.setBackground(UIManager.getColor("InternalFrame.activeTitleBackground") != null ?
            UIManager.getColor("InternalFrame.activeTitleBackground") :
            new Color(240, 240, 240));
        recursosPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        tabbedPane.addTab("Recursos", null, recursosPanel, null);

        modelRecursos = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        String[] headerRecursos = {"ID", "Nombre", "Tipo", "Disponibilidad"};
        modelRecursos.setColumnIdentifiers(headerRecursos);

        tableRecursos = new JTable(modelRecursos);
        tableRecursos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollRecursos = new JScrollPane(tableRecursos);
        scrollRecursos.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        recursosPanel.add(scrollRecursos);

        // Panel de botón
        JPanel buttonPane = new JPanel();
        buttonPane.setBackground(UIManager.getColor("InternalFrame.activeTitleGradient") != null ?
            UIManager.getColor("InternalFrame.activeTitleGradient") :
            new Color(240, 240, 240));
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton closeButton = new JButton("Cerrar");
        closeButton.setFont(new Font("Tahoma", Font.BOLD, 13));
        closeButton.addActionListener(e -> dispose());
        buttonPane.add(closeButton);

        loadData();
    }

    private int contarTrabajosTotal() {
        int total = 0;
        for (Comision comision : evento.getComisiones()) {
            total += comision.getTrabajos().size();
        }
        return total;
    }

    private void loadData() {
        // Cargar comisiones
        modelComisiones.setRowCount(0);
        for (Comision comision : evento.getComisiones()) {
            Object[] row = {
                comision.getCodComision(),
                comision.getNombre(),
                comision.getArea(),
                comision.getJurado().size(),
                comision.getTrabajos().size()
            };
            modelComisiones.addRow(row);
        }

        // Cargar recursos
        modelRecursos.setRowCount(0);
        for (Recurso recurso : evento.getRecursos()) {
            Object[] row = {
                recurso.getId(),
                recurso.getNombre(),
                recurso.getTipo(),
                recurso.getDisponibilidad() ? "Disponible" : "No Disponible"
            };
            modelRecursos.addRow(row);
        }
    }
}