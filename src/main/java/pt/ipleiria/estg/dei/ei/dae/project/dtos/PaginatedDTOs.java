package pt.ipleiria.estg.dei.ei.dae.project.dtos;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class PaginatedDTOs<DTO> implements Serializable {

    public final class Metadata implements Serializable {
        private final Long count;
        private final Long totalCount;

        public Metadata(Long count, Long totalCount) {
            this.count = count;
            this.totalCount = totalCount;
        }

        public Metadata(Long totalCount) {
            this(0L, totalCount);
        }

        public Long getCount() {
            return count;
        }

        public Long getTotalCount() {
            return totalCount;
        }
    }

    private final List<DTO> data;

    private final Metadata metadata;

    public PaginatedDTOs(long totalCount) {
        this.data = Collections.emptyList();
        this.metadata = new Metadata(0L, totalCount);
    }

    public PaginatedDTOs(List<DTO> data, long totalCount, int offset) {
        this.data = data;
        this.metadata = data.isEmpty() ? new Metadata(0L, totalCount) : new Metadata((long) (offset + data.size()), totalCount);
    }

    public List<DTO> getData() {
        return data;
    }

    public Metadata getMetadata() {
        return metadata;
    }
}
