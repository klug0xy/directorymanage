package fr.amu.directorymanage.test;

import com.github.springtestdbunit.dataset.AbstractDataSetLoader;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.springframework.core.io.Resource;
import java.io.InputStream;

public class ColumnSensingFlatXMLDataSetLoader extends AbstractDataSetLoader {
	// @Override
	// protected IDataSet createDataSet(Resource resource) throws Exception {
	// FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
	// builder.setColumnSensing(true);
	// try (InputStream inputStream = resource.getInputStream()) {
	// return builder.build(inputStream);
	// }
	// }

	@Override
	protected IDataSet createDataSet(Resource resource) throws Exception {
		FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
		builder.setColumnSensing(true);
		InputStream inputStream = resource.getInputStream();
		try {
			return builder.build(inputStream);
		} finally {
			inputStream.close();
		}
	}
}