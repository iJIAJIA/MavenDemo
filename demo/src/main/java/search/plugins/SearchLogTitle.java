package search.plugins;

public enum SearchLogTitle implements ExcelTitle{
	;
	private int index;
	
	private String title;
	
	private SearchLogTitle(int index,String title){
		this.index = index;
		this.title = title;
	}
	@Override
	public int getColumnIdx() {
		return this.index;
	}
	@Override
	public String getColumnTitle() {
		return this.title;
	}
}
