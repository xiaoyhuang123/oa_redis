package com.ft.backend.work.model;

public class PositionTitle {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column position_title.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column position_title.position_title_name
     *
     * @mbggenerated
     */
    private String positionTitleName;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column position_title.id
     *
     * @return the value of position_title.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column position_title.id
     *
     * @param id the value for position_title.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column position_title.position_title_name
     *
     * @return the value of position_title.position_title_name
     *
     * @mbggenerated
     */
    public String getPositionTitleName() {
        return positionTitleName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column position_title.position_title_name
     *
     * @param positionTitleName the value for position_title.position_title_name
     *
     * @mbggenerated
     */
    public void setPositionTitleName(String positionTitleName) {
        this.positionTitleName = positionTitleName == null ? null : positionTitleName.trim();
    }
}