package cn.comm;

public class Address {
	private String addressName;
	private String postCode;

	public Address() {
	}

	public Address(String addressName, String postCode) {
		this.addressName = addressName;
		this.postCode = postCode;
	}

	public String getAddressName() {
		return addressName;
	}

	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

}
