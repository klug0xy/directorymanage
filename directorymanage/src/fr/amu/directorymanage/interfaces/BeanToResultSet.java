package fr.amu.directorymanage.interfaces;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface BeanToResultSet<T> {
	ResultSet toResultSet(T bean) throws SQLException;
}