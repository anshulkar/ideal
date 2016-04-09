package ideal.com.utk.ideal.JSON;

/**
 * Created by Utkarsh on 04-04-2016 with the help of SWAG.
 */
public class LeaveDataJSONSchema {
    public int id = -1;//make sure the id is non negative in DB
    public String username = null ,
            type = null ,//TODO:api currently doesnt return the type of leave;
            nature = null ,
            leaveStart = null,
            leaveEnd = null ,
            grounds = null ,
            recommender = null ,
            approver = null ,
            recommenderComment = null ,
            approverComment = null ,
            status = null;

    public LeaveDataJSONSchema(){}

    public LeaveDataJSONSchema(int id ,String type, String nature,String leaveStart, String leaveEnd ,String grounds, String recommender,String approver,String recommenderComment, String approverComment,String status ) {
        this.id = id;
        this.type = type;
        this.nature = nature;
        this.leaveStart = leaveStart;
        this.leaveEnd = leaveEnd;
        this.grounds = grounds;
        this.recommender = recommender;
        this.approver = approver;
        this.recommenderComment = recommenderComment;
        this.approverComment = approverComment;
        this.status = status;
    }
}
//TODO:split the schema for general user , approver, recommender in different classes and inherit each other