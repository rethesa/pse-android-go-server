package edu.kit.pse.bdhkw.server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.ManyToOne;

import edu.kit.pse.bdhkw.common.model.SimpleUser;
@Entity
@Table(name="membership",
	uniqueConstraints={@UniqueConstraint(columnNames={"mem_id"})}
)
public class MemberAssociation {
	private int id;
	private SimpleUser user;
	private GroupServer group;
	private boolean isAdmin;
	private boolean isStatusGo;
	
	public MemberAssociation() {
		// TODO Auto-generated constructor stub
	}
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="mem_id",unique=true,nullable=false)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	@Column(name="is_admin")
	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	@Column(name="is_go")
	public boolean isStatusGo() {
		return isStatusGo;
	}

	public void setStatusGo(boolean isStatusGo) {
		this.isStatusGo = isStatusGo;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="device_id",nullable=false)
	public SimpleUser getUser() {
		return user;
	}

	public void setUser(SimpleUser user) {
		this.user = user;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="group_name")
	public GroupServer getGroup() {
		return group;
	}

	public void setGroup(GroupServer group) {
		this.group = group;
	}
}
