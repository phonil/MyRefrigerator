package opg.app.myrefrigerator;

// 아이템 정보들
public class RefrigeratorRightData {

    private String tv_name, tv_number, tv_data, tv_where;

    public RefrigeratorRightData(String tv_name, String tv_number, String tv_data, String tv_where) {
        this.tv_name = tv_name;
        this.tv_number = tv_number;
        this.tv_data = tv_data;
        this.tv_where = tv_where;
    }

    public String getTv_name() {
        return tv_name;
    }

    public void setTv_name(String tv_name) {
        this.tv_name = tv_name;
    }

    public String getTv_number() {
        return tv_number;
    }

    public void setTv_number(String tv_number) {
        this.tv_number = tv_number;
    }

    public String getTv_data() {
        return tv_data;
    }

    public void setTv_data(String tv_data) {
        this.tv_data = tv_data;
    }

    public String getTv_where() {
        return tv_where;
    }

    public void setTv_where(String tv_where) {
        this.tv_where = tv_where;
    }
}
