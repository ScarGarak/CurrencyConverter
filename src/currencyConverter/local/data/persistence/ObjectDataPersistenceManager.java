package currencyConverter.local.data.persistence;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author mortlust
 *
 * Interface for persistent storage of data in serialized files
 */
public class ObjectDataPersistenceManager implements DataPersistenceManager {
	
	private ObjectInputStream ois = null;
	private ObjectOutputStream oos = null;

	public void openForReading(String dataSource) throws IOException {
		System.out.println("In openForReading");
		ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(dataSource)));		
	}

	public void openForWriting(String dataSource) throws IOException {
		oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(dataSource)));
	}

	public boolean close() {
		if(ois != null)
			try {
				ois.close();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		if(oos != null)
			try {
				oos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return true;
	}

}
