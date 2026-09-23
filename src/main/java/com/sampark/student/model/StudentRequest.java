package com.sampark.student.model;

public class StudentRequest {
	
	    private int id;
	    private String roll;
	    private String name;
        private boolean status=true;
	    private String className;
	    private String address;
	    private String email;
	    private String mobile;
	    private int fees;
	    private String course;

	    public StudentRequest()
	    {
	    	
	    }

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		
		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getMobile() {
			return mobile;
		}

		public void setMobile(String mobile) {
			this.mobile = mobile;
		}

		public int getFees() {
			return fees;
		}

		public void setFees(int fees) {
			this.fees = fees;
		}

		public String getClassName() {
			return className;
		}

		public void setClassName(String className) {
			this.className = className;
		}

		public String getCourse() {
			return course;
		}

		public void setCourse(String course) {
			this.course = course;
		}


		public boolean isStatus() {
			return status;
		}

//		public void setStatus(boolean status) {
//			this.status = status;
//		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getRoll() {
			return roll;
		}

		public void setRoll(String roll) {
			this.roll = roll;
		}
		
		

		
		
		
		
		
	    
	    
}

//package com.sampark.student.model;


