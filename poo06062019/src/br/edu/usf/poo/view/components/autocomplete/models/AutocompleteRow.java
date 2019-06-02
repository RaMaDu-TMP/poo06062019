package br.edu.usf.poo.view.components.autocomplete.models;


import java.util.ArrayList;
import java.util.List;

public class AutocompleteRow<T> {

	private T item;
	private List<String> columns;
	
	public AutocompleteRow(T item) {
		this.item = item;
		this.columns = new ArrayList<>();
		columns.add(item.toString());
	}
	
	public AutocompleteRow(T item, List<String> columns) {
		this.item = item;
		this.columns = columns;
	}
	
	public T getItem() {
		return item;
	}
	
	public void setItem(T item) {
		this.item = item;
	}
	
	public List<String> getColumns() {
		return columns;
	}
	
	public void setColumns(List<String> columns) {
		this.columns = columns;
	}

	public Object getColumn(int columnIndex) {
		return columns.get(columnIndex);
	}
	
}
