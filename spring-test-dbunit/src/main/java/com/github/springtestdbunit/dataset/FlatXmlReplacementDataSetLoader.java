package com.github.springtestdbunit.dataset;

import java.io.InputStream;
import java.util.Calendar;
import java.util.Locale;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.springframework.core.io.Resource;
/**
 * A {@link DataSetLoader data set loader} that can be used to load {@link FlatXmlDataSet xml datasets}
 * and use ReplacementDataSet to replace special values
 * 
 * @author guoyun
 */
public class FlatXmlReplacementDataSetLoader extends AbstractDataSetLoader {

	@Override
	protected IDataSet createDataSet(Resource resource) throws Exception {
		FlatXmlDataSetBuilder builder = null;
		InputStream inputStream = null;
		IDataSet dataSet = null;
		ReplacementDataSet replaceDataSet = null;
		
		try {
			builder = new FlatXmlDataSetBuilder();
			inputStream = resource.getInputStream();
			dataSet = builder.build(inputStream);
			replaceDataSet = new ReplacementDataSet(dataSet);
			Calendar cal = Calendar.getInstance(Locale.getDefault());
			replaceDataSet.addReplacementObject("[NULL]", null);
			replaceDataSet.addReplacementObject("[NOW]", cal.getTime());
			replaceDataSet.addReplacementObject("", null);
		} finally {
			if(inputStream != null)
				inputStream.close();
		}
		
		return replaceDataSet;
	}

}
