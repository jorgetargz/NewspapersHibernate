package domain.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;


@Getter
@Setter

@Entity
@Table(name = "subscribe")

@NamedQuery(name = "HQL_GET_ALL_ACTIVE_SUBSCRIBES_BY_READER",
        query = "SELECT s FROM Subscribe s " +
                "WHERE s.readerByIdReader = :reader " +
                "and s.cancellationDate is null")

@NamedQuery(name = "HQL_DELETE_ALL_SUBSCRIBES_BY_READER",
        query = "delete from Subscribe s " +
                "where s.readerByIdReader = :reader")

public class Subscribe {

    @Id
    @Column(name = "id_newspaper")
    private int idNewspaper;

    @Id
    @Column(name = "id_reader")
    private int idReader;

    @Column(name = "cancellation_date")
    private LocalDate cancellationDate;

    @Column(name = "signing_date")
    private LocalDate signingDate;

    @ManyToOne
    @JoinColumn(name = "id_reader", referencedColumnName = "id", insertable = false, updatable = false)
    private Reader readerByIdReader;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subscribe that = (Subscribe) o;
        return idNewspaper == that.idNewspaper && idReader == that.idReader && Objects.equals(cancellationDate, that.cancellationDate) && Objects.equals(signingDate, that.signingDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idNewspaper, idReader, cancellationDate, signingDate);
    }
}
