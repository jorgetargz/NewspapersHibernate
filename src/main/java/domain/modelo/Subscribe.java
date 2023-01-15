package domain.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;


@Getter
@Setter

@Entity
@Table(name = "subscribe")
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
    @JoinColumn(name = "id_newspaper", referencedColumnName = "id", insertable = false, updatable = false)
    private Newspaper newspaperByIdNewspaper;

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
