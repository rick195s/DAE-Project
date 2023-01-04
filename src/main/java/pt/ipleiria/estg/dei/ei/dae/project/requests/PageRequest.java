package pt.ipleiria.estg.dei.ei.dae.project.requests;

import javax.validation.constraints.PositiveOrZero;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;

public class PageRequest {
    @QueryParam("offset")
    @DefaultValue("0")
    @PositiveOrZero
    private int offset;

    @QueryParam("limit")
    @DefaultValue("10")
    @PositiveOrZero
    private int limit;

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    @Override
    public String toString() {
        return "PageRequest { offset: " + offset + ", limit:" + limit + " }";
    }
}
