package cn.sise.oa.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * 会议室基本信息
 * @author yzh
 *
 */
public class MeetingRoom {

	private Integer id;
	private String name;
	private String position;
	private int capacity; //可容纳人数
	private String equipmentCases; //设配情况
	
	private Set<Reservation> reservations = new HashSet<Reservation>();
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}	
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	public String getEquipmentCases() {
		return equipmentCases;
	}
	public void setEquipmentCases(String equipmentCases) {
		this.equipmentCases = equipmentCases;
	}
	public Set<Reservation> getReservations() {
		return reservations;
	}
	public void setReservations(Set<Reservation> reservations) {
		this.reservations = reservations;
	}
	
	
}
