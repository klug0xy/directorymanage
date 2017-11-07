package fr.amu.directorymanage.interfaces;

import java.sql.SQLException;

public interface ResultSetToBean<T> {
	   T toBean(java.sql.ResultSet rs) throws SQLException;
}