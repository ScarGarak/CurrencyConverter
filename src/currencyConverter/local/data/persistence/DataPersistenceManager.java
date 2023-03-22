package currencyConverter.local.data.persistence;

import java.io.IOException;

/**
 * 
 * @author mortlust
 *
 * Interface for access to storage media to save Currency data
 * 
 * The interface must be implemented from classes in case you want to realize a data persistence-interface
 */
public interface DataPersistenceManager {
	
	/**
	 * methods for opening/closing an external data source
	 * 
	 * @param dataSource
	 * @throws IOException
	 */
	
	public void openForReading(String dataSource) throws IOException;
	
	public void openForWriting(String dataSource) throws IOException;
	
	public boolean close();
	
	/**
	 * method to read in an external data source
	 * 
	 * @return
	 * @throws IOException
	 */
//	public Currency loadCurrency() throws IOException;
//	
//	/**
//	 * method to write to an external data source
//	 * 
//	 * @return
//	 * @throws IOException
//	 */
//	public void saveCurrency(Currency c) throws IOException;
	
	
	
	
}
