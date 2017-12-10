/*
 * Copyright December 2017 the original author or authors.
 * 
 * Project released in an university setting
 *
 */

package fr.amu.directorymanage.test;

import com.github.springtestdbunit.dataset.AbstractDataSetLoader;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.springframework.core.io.Resource;
import java.io.InputStream;

/**
 * 
 * Classe pour configurer le dataSetLoader de dbunit
 * et le mettre sensible a la case 
 * "columnSensing property" pour le FlatXmlBuilder
 *   
 * @author Houssem Mjid
 * @author Mohamad Abdelnabi
 *  
 */

public class ColumnSensingFlatXMLDataSetLoader extends AbstractDataSetLoader {

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