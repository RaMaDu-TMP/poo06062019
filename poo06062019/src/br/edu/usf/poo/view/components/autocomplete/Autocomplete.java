package br.edu.usf.poo.view.components.autocomplete;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.DefaultFocusTraversalPolicy;
import java.awt.Dimension;
import java.awt.FocusTraversalPolicy;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.KeyboardFocusManager;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.google.common.base.Strings;

import br.edu.usf.poo.utils.IconCatalog;
import br.edu.usf.poo.view.components.autocomplete.document.DocumentAdapter;
import br.edu.usf.poo.view.components.autocomplete.models.AutocompleteRow;
import br.edu.usf.poo.view.components.autocomplete.renderer.TableItemsRenderer;
import br.edu.usf.poo.view.components.ui.CompoundTextField;
import br.edu.usf.poo.view.components.ui.ScrollBarUI;
	
public class Autocomplete<T> extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -709151074459283002L;

	private static final int POPUP_MIN_HEIGTH = 52;
	
	private TableItemsModel itemsModel;
	
	private T selectedItem;
	
	private boolean alphabeticalOrder = true;
	private int popupMaxHeigth = 200;
	
	private Comparator<? super AutocompleteRow<T>> rowComparator;
	
	public Autocomplete() {
		initComponents();
		
		searchField.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, new HashSet<>());
		searchField.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, new HashSet<>());
        
		rowComparator = initComparator();
		
		// Scroll bar configurations
		ScrollBarUI.config(scrollPane);
		scrollPane.getVerticalScrollBar().setUnitIncrement(15);
		
		// Renderer
		TableCellRenderer renderer = new TableItemsRenderer();
		tblItems.setDefaultRenderer(Object.class, renderer);
		
		// Initialize table model
		itemsModel = new TableItemsModel();
		tblItems.setModel(itemsModel);
		
		// Delete table headers
		tblItems.setTableHeader(null);
		
		// Configure key pop up hiding
		Object hidePopupKey = new JComboBox<>().getClientProperty("doNotCancelPopup");
		btnOptions.putClientProperty("doNotCancelPopup", hidePopupKey);
		lblSelected.putClientProperty("doNotCancelPopup", hidePopupKey);
		
		configureFocusPolicy();
		
		initDocument();
		
		initTableSelectionListener();
	}

	private void initTableSelectionListener() {
		
		tblItems.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent e) {
				setSelectedItemText(getSelectedItemTable());
				
				hidePopup();
			}
		});
	}

	private void initDocument() {
		searchField.getDocument().addDocumentListener(new DocumentAdapter() {
			
			@Override
			public void textChanged(DocumentEvent e) {
				filter(searchField.getText());
			}
		});
	}

	private Comparator<AutocompleteRow<T>> initComparator() {
		return new Comparator<AutocompleteRow<T>>() {
			
			@Override
			public int compare(AutocompleteRow<T> a, AutocompleteRow<T> b) {
				List<String> columnsA = a.getColumns();
				List<String> columnsB = b.getColumns();

				int compareTo = 0;
				
				for (int i = 0; i < columnsA.size(); i++) {
					
					String colA = columnsA.get(i);
					String colB = columnsB.get(i);
					
					if (colA != null) {
						compareTo = colA.compareTo(colB);
						
						if (compareTo != 0) {
							break;
						}
					}
				}
				
				return compareTo;
			}
			
		};
	}

	private void configureFocusPolicy() {
		FocusTraversalPolicy traversalPolicy = new DefaultFocusTraversalPolicy() {
			private static final long serialVersionUID = 1360929224508084816L;
			
			@Override
			public Component getFirstComponent(Container aContainer) {
				return btnOptions;
			}
			
			@Override
			public Component getLastComponent(Container aContainer) {
				return btnOptions;
			}
		};
		
		setFocusTraversalPolicyProvider(true);
		setFocusTraversalPolicy(traversalPolicy);
		
		FocusTraversalPolicy traversalPolicyPopup = new DefaultFocusTraversalPolicy() {
			private static final long serialVersionUID = 1360929224508084816L;
			
			@Override
			public Component getFirstComponent(Container aContainer) {
				return searchField;
			}
			
			@Override
			public Component getLastComponent(Container aContainer) {
				return searchField;
			}
		};
		
		popup.setFocusTraversalPolicyProvider(true);
		popup.setFocusTraversalPolicy(traversalPolicyPopup);
	}
	
	private T getSelectedItemTable() {
		int row = tblItems.getSelectedRow();
		T valueAt = null;
		
		if (row >= 0) {
			row = tblItems.convertRowIndexToModel(row);
			
			valueAt = itemsModel.getValueAtRow(row);
		}
		
		selectedItem = valueAt;
		
		return valueAt;
	}
	
	public void setSelectedItemText(T item) {
		String text = "";
		if (item != null) {
			text = item.toString();
		}
		
		lblSelected.setText(text);
		lblClear.setVisible(!Strings.isNullOrEmpty(text));
	}
	
	public void setItems(List<T> items) {
		List<AutocompleteRow<T>> list = items.stream().map(item -> new AutocompleteRow<>(item))
				.collect(Collectors.toList());
		
		setItems(list, 1);
	}
	
	public void setItems(List<AutocompleteRow<T>> items, int col) {
		setItems(items, col, null);
	}
	
	public void setItems(List<AutocompleteRow<T>> items, int col, Integer mainColumn) {

		if (alphabeticalOrder) {
			items.sort(rowComparator);
		}
		
		itemsModel.setData(items);
		itemsModel.setColumnCount(col);
		
		tblItems.setModel(itemsModel);
		
		if (mainColumn != null) {
			int defaultWidth = tblItems.getColumnModel().getColumn(0).getPreferredWidth();

			tblItems.getColumnModel().getColumn(mainColumn).setPreferredWidth(defaultWidth * 4);
		}
		
		SwingUtilities.invokeLater(() -> {
			updateRowHeights();
		});
	}

	private void updateRowHeights() {

		for (int row = 0; row < tblItems.getRowCount(); row++) {
			int rowHeight = 22;
			
			for (int column = 0; column < tblItems.getColumnCount(); column++) {
				TableItemsRenderer comp = (TableItemsRenderer) tblItems.prepareRenderer(tblItems.getCellRenderer(row, column), row, column);

				String text = comp.getText();
				
				int width = tblItems.getColumnModel().getColumn(column).getWidth();
				
				if (width > 0 && !Strings.isNullOrEmpty(text)) {
					FontMetrics fm = getFontMetrics(getFont());
					String str = text.replaceAll("<html>", "");
					str = str.replaceAll("</html>", "");
					
					if (fm.stringWidth(str) > width) {
						rowHeight = ((fm.stringWidth(str) / width) + 1) * 22;
					}
				}
				
//				setPreferredSize(new Dimension(width, rowHeight));
//				rowHeight = Math.max(rowHeight, comp.getPreferredSize().height);
			}
			
			tblItems.setRowHeight(row, rowHeight);
		}
	}
		
//String text = value.toString();
//		
//		int height = 22;
//		int width = table.getColumnModel().getColumn(column).getWidth();
//		
//		if (width > 0 && !Strings.isNullOrEmpty(text)) {
//			FontMetrics fm = getFontMetrics(getFont());
//			String str = text.replaceAll("<html>", "");
//			str = str.replaceAll("</html>", "");
//			
//			if (fm.stringWidth(str) > width) {
//				height = ((fm.stringWidth(str) / width) + 1) * 22;
//			}
//		}
//		
//		setPreferredSize(new Dimension(width, height));
//		
//		for (int row = 0; row < tblItems.getRowCount(); row++) {
//
//			
//			int rowHeight = tblItems.getRowHeight();
//
//			for (int column = 0; column < tblItems.getColumnCount(); column++) {
//				Component comp = tblItems.prepareRenderer(tblItems.getCellRenderer(row, column), row, column);
//
//				rowHeight = Math.max(rowHeight, comp.getPreferredSize().height);
//			}
//			tblItems.setRowHeight(row, rowHeight);
//		}
//	}
////		for (int row = 0; row < tblItems.getRowCount(); row++) {
////			int rowHeight = tblItems.getRowHeight(row);
////
////			for (int column = 0; column < tblItems.getColumnCount(); column++) {
////				Component comp = tblItems.prepareRenderer(tblItems.getCellRenderer(row, column), row, column);
////				
////				int h = 20;
////				int w = comp.getSize().width;
////
////				if (w == 0 || !(comp instanceof TableItemsRenderer)) {
////					return;
////				}
////				
////				TableItemsRenderer textComponent = (TableItemsRenderer) comp;
////				
////				String text = textComponent.getText();
////				
////				if (!Strings.isNullOrEmpty(text)) {
////					FontMetrics fm = textComponent.getFontMetrics(textComponent.getFont());
////					String str = text.replaceAll("<html>", "");
////					str = str.replaceAll("</html>", "");
////
////					if (fm.stringWidth(str) > w) {
////						h *= ((fm.stringWidth(text) / w) + 1);
////					}
////					rowHeight = Math.max(rowHeight, h);
////					
//////					textComponent.setSize(new Dimension(w, h));
//////					textComponent.setPreferredSize(new Dimension(w, h));
//////				} else {
////////					textComponent.setSize(new Dimension(0, 0));
//////					textComponent.setPreferredSize(new Dimension(0, 0));
////				}
////				
////			}
////
////			tblItems.setRowHeight(row, rowHeight);
////		}
//	}
	
	private void filter(String search) {
		TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(tblItems.getModel());
		rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + search));
		
		tblItems.setRowSorter(rowSorter);
		
		boolean hasItems = tblItems.getRowCount() > 0;
		pnlNotFound.setVisible(!hasItems);
		scrollPane.setVisible(hasItems);
		
		resizePopup();
		
		searchField.requestFocus();
	}
	
	public void clearFilter() {
		searchField.setText("");
	}
	
	private void tooglePopup() {
		if (popup.isVisible()) {
			hidePopup();
		} else {
			showPopup();
		}
	}

	private int getBestHeigth() {
		int h = 0;
		
		for (int i = 0; i < tblItems.getRowCount(); i++) {
			h += tblItems.getRowHeight(i);
			
			if (h >= popupMaxHeigth) {
				return popupMaxHeigth;
			}
		}
		
		if (h < POPUP_MIN_HEIGTH) {
			return POPUP_MIN_HEIGTH;
		}
		
		return h;
	}
	
	private void tableUpAction() {
		ListSelectionModel selectionModel = tblItems.getSelectionModel();
		int currentIndex = selectionModel.getMaxSelectionIndex();
		int index = currentIndex - 1;
		
		if (index < 0) {
			index = 0;
		}
		
		if (index > tblItems.getRowCount()) {
			index = tblItems.getRowCount();
		}
		
		selectionModel.setSelectionInterval(index, index);
	}
	
	private void tableDownAction() {
		ListSelectionModel selectionModel = tblItems.getSelectionModel();
		int currentIndex = selectionModel.getMaxSelectionIndex();
		int index = currentIndex + 1;
		
		if (index < 0) {
			index = 0;
		}
		
		if (index > tblItems.getRowCount()) {
			index = tblItems.getRowCount();
		}
		
		selectionModel.setSelectionInterval(index, index);
	}
	
	private void showPopup() {
		clearFilter();
		
		resizePopup();
		
		updateRowHeights();
		
		if (tblItems.getSelectionModel().getMaxSelectionIndex() < 0) {
			tblItems.getSelectionModel().setSelectionInterval(0, 0);
		}
		
		updateScrollPosition();
		
		popup.show(this, 0, getHeight());

		SwingUtilities.invokeLater(() -> {
			searchField.requestFocus();
		});
	}

	private void resizePopup() {
		popup.setPopupSize(getWidth(), getBestHeigth());
	}
	
	private void hidePopup() {
		popup.setVisible(false);
	}
	
	private void tableEnterAction() {
		setSelectedItemText(getSelectedItemTable());

		hidePopup();
	}
	
	public boolean isAlphabeticalOrder() {
		return alphabeticalOrder;
	}
	
	public void setAlphabeticalOrder(boolean alphabeticalOrder) {
		this.alphabeticalOrder = alphabeticalOrder;
	}

	private void btnOptionsActionPerformed(ActionEvent e) {
		tooglePopup();
	}

	private void lblSelectedMousePressed(MouseEvent e) {
		tooglePopup();
	}

	private void searchFieldKeyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			tableUpAction();
			break;

		case KeyEvent.VK_DOWN:
			tableDownAction();
			break;
			
		case KeyEvent.VK_ENTER:
			tableEnterAction();
			break;
			
		case KeyEvent.VK_TAB:
			setSelectedItemText(getSelectedItemTable());
			hidePopup();
			
			if (e.isShiftDown()) {
				btnOptions.transferFocusBackward();
			} else {
				btnOptions.transferFocus();
			}
			break;
			
		default:
			break;
		}
		
		updateScrollPosition();
		
		searchField.requestFocus();
	}

	private void updateScrollPosition() {
		Rectangle rect = tblItems.getCellRect(tblItems.getSelectedRow(), 0, true);
		tblItems.scrollRectToVisible(rect);
	}

	private void btnOptionsFocusGained(FocusEvent e) {
		pnlSelected.setBackground(new Color(0, 0, 0, 25));
	}

	private void btnOptionsFocusLost(FocusEvent e) {
		pnlSelected.setBackground(Color.WHITE);
	}
	
	private void searchFieldMouseWheelMoved(MouseWheelEvent e) {
		e.consume();
	}

	private void lblNotFoundMouseWheelMoved(MouseWheelEvent e) {
		e.consume();
	}

	private void lblClearMousePressed(MouseEvent e) {
		clearFilter();
		setSelectedItem(null);
	}

	public T getSelectedItem() {
		return selectedItem;
	}
	
	public void setSelectedItem(T selectedItem) {
		this.selectedItem = selectedItem;
		setSelectedItemText(selectedItem);
	}
	
	class TableItemsModel extends DefaultTableModel {
		private static final long serialVersionUID = 4036374890044769868L;

		private List<AutocompleteRow<T>> data;
		
		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			return data.get(rowIndex).getColumn(columnIndex);
		}
		
		public T getValueAtRow(int rowIndex) {
			return data.get(rowIndex).getItem();
		}
		
		@Override
		public int getRowCount() {
			if (data != null) {
				return data.size();
			}
			return 0;
		}
		
		public List<AutocompleteRow<T>> getData() {
			return data;
		}
		
		public void setData(List<AutocompleteRow<T>> data) {
			this.data = data;
		}
	}
	
	private void initComponents() {
		pnlSelected = new JPanel();
		lblSelected = new JLabel();
		lblClear = new JLabel();
		btnOptions = new JButton();
		popup = new JPopupMenu();
		pnlPopupContent = new JPanel();
		pnlSearchFieldPosition = new JPanel();
		searchField = new CompoundTextField();
		pnlNotFound = new JPanel();
		lblNotFound = new JLabel();
		scrollPane = new JScrollPane();
		tblItems = new JTable();

		//======== this ========
		setOpaque(false);
		setBorder(new MatteBorder(1, 1, 1, 1, Color.gray));
		setMinimumSize(new Dimension(92, 24));
		setFocusTraversalPolicyProvider(true);
		setLayout(new BorderLayout());

		//======== pnlSelected ========
		{
			pnlSelected.setBackground(Color.white);
			pnlSelected.setLayout(new BorderLayout());

			//---- lblSelected ----
			lblSelected.setFocusable(false);
			lblSelected.setRequestFocusEnabled(false);
			lblSelected.setVerifyInputWhenFocusTarget(false);
			lblSelected.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					lblSelectedMousePressed(e);
				}
			});
			pnlSelected.add(lblSelected, BorderLayout.CENTER);

			//---- lblClear ----
			lblClear.setText("x");
			lblClear.setFont(new Font("Arial", Font.PLAIN, 14));
			lblClear.setPreferredSize(new Dimension(15, 15));
			lblClear.setHorizontalTextPosition(SwingConstants.CENTER);
			lblClear.setHorizontalAlignment(SwingConstants.CENTER);
			lblClear.setVisible(false);
			lblClear.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					lblClearMousePressed(e);
				}
			});
			pnlSelected.add(lblClear, BorderLayout.EAST);
		}
		add(pnlSelected, BorderLayout.CENTER);

		//---- btnOptions ----
		btnOptions.setIcon(IconCatalog.gi().getIcon("arrow.png"));
		btnOptions.setBorderPainted(false);
		btnOptions.setBorder(null);
		btnOptions.setFocusPainted(false);
		btnOptions.setPreferredSize(new Dimension(33, 22));
		btnOptions.setBackground(Color.white);
		btnOptions.setVerifyInputWhenFocusTarget(false);
		btnOptions.setComponentPopupMenu(popup);
		btnOptions.addActionListener(e -> btnOptionsActionPerformed(e));
		btnOptions.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				btnOptionsFocusGained(e);
			}
			@Override
			public void focusLost(FocusEvent e) {
				btnOptionsFocusLost(e);
			}
		});
		add(btnOptions, BorderLayout.EAST);

		//======== popup ========
		{
			popup.setBorder(new MatteBorder(1, 1, 1, 1, Color.lightGray));
			popup.setInvoker(this);

			//======== pnlPopupContent ========
			{
				pnlPopupContent.setOpaque(false);
				pnlPopupContent.setFocusable(false);
				pnlPopupContent.setRequestFocusEnabled(false);
				pnlPopupContent.setVerifyInputWhenFocusTarget(false);
				pnlPopupContent.setBorder(new EmptyBorder(3, 3, 2, 3));
				pnlPopupContent.setLayout(new BoxLayout(pnlPopupContent, BoxLayout.Y_AXIS));

				//======== pnlSearchFieldPosition ========
				{
					pnlSearchFieldPosition.setBorder(new EmptyBorder(0, 0, 3, 0));
					pnlSearchFieldPosition.setOpaque(false);
					pnlSearchFieldPosition.setLayout(new BoxLayout(pnlSearchFieldPosition, BoxLayout.X_AXIS));

					//---- searchField ----
					searchField.setFont(new Font("Arial", Font.PLAIN, 13));
					searchField.setPreferredSize(new Dimension(24, 24));
					searchField.setIcon(IconCatalog.gi().getIcon("search.png"));
					searchField.setIconDimension(new Dimension(20, 20));
					searchField.setMinimumSize(new Dimension(24, 24));
					searchField.setBorder(new MatteBorder(1, 1, 1, 1, Color.lightGray));
					searchField.setBackground(Color.WHITE);
					searchField.addKeyListener(new KeyAdapter() {
						@Override
						public void keyPressed(KeyEvent e) {
							searchFieldKeyPressed(e);
						}
					});
					searchField.addMouseWheelListener(e -> searchFieldMouseWheelMoved(e));
					pnlSearchFieldPosition.add(searchField);
				}
				pnlPopupContent.add(pnlSearchFieldPosition);

				//======== pnlNotFound ========
				{
					pnlNotFound.setOpaque(false);
					pnlNotFound.setVisible(false);
					pnlNotFound.setLayout(new BorderLayout());

					//---- lblNotFound ----
					lblNotFound.setText("Sem match");
					lblNotFound.setPreferredSize(new Dimension(87, 20));
					lblNotFound.setHorizontalAlignment(SwingConstants.CENTER);
					lblNotFound.setOpaque(true);
					lblNotFound.setBackground(SystemColor.info);
					lblNotFound.addMouseWheelListener(e -> lblNotFoundMouseWheelMoved(e));
					pnlNotFound.add(lblNotFound, BorderLayout.CENTER);
				}
				pnlPopupContent.add(pnlNotFound);

				//======== scrollPane ========
				{
					scrollPane.setBorder(BorderFactory.createEmptyBorder());
					scrollPane.setViewportBorder(null);
					scrollPane.setAutoscrolls(true);

					//---- tblItems ----
					tblItems.setShowHorizontalLines(false);
					tblItems.setShowVerticalLines(false);
					tblItems.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
					tblItems.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					tblItems.setSelectionBackground(new Color(57, 105, 138));
					tblItems.setSelectionForeground(Color.white);
					tblItems.setBorder(null);
					tblItems.setFocusable(false);
					scrollPane.setViewportView(tblItems);
				}
				pnlPopupContent.add(scrollPane);
			}
			popup.add(pnlPopupContent);
		}
	}

	private JPanel pnlSelected;
	private JLabel lblSelected;
	private JLabel lblClear;
	private JButton btnOptions;
	private JPopupMenu popup;
	private JPanel pnlPopupContent;
	private JPanel pnlSearchFieldPosition;
	private CompoundTextField searchField;
	private JPanel pnlNotFound;
	private JLabel lblNotFound;
	private JScrollPane scrollPane;
	private JTable tblItems;
}
