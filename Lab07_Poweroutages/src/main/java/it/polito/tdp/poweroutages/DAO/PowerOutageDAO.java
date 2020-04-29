package it.polito.tdp.poweroutages.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.PowerOutage;

public class PowerOutageDAO {
	
	public List<Nerc> getNercList() {

		String sql = "SELECT id, value FROM nerc";
		List<Nerc> nercList = new ArrayList<>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Nerc n = new Nerc(res.getInt("id"), res.getString("value"));
				nercList.add(n);
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return nercList;
	}
	
	
	public List<PowerOutage> getPowerOutage(int idNerc) {
		
		String sql = "SELECt id, customers_affected, YEAR(date_event_began) AS 'year', TIMEDIFF(date_event_finished, date_event_began) AS 'time' " + 
				"FROM PowerOutages " + 
				"WHERE nerc_id = ? " +
				"ORDER BY year ASC";
		List<PowerOutage> powerOutageList = new ArrayList<>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, idNerc);
			
			ResultSet res = st.executeQuery();
			
			while(res.next()) {
				LocalTime time = res.getTime("time").toLocalTime();
				PowerOutage po = new PowerOutage(res.getInt("id"), res.getInt("customers_affected"),
						time, res.getInt("year"));
				
				powerOutageList.add(po);
			}
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return powerOutageList;
	}
	
}
