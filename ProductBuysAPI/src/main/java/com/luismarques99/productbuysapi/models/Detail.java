package com.luismarques99.productbuysapi.models;

import lombok.*;
import org.springframework.beans.factory.annotation.Required;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * Class that represents the structure of a {@link Purchase purchase} detail.
 *
 * @author Luis Marques
 */
@Entity
@Table(name = "detail")
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class Detail {

    /**
     * Numerical idenifier.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @EqualsAndHashCode.Exclude
    private Long id;

    /**
     * Description.
     */
    @Column(name = "description")
    @Size(max = 100)
    @NonNull
    @Setter
    private String description;

    /**
     * Quantity.
     */
    @Column(name = "quantity")
    @NonNull
    @Setter
    private Integer quantity;

    /**
     * Value.
     */
    @Column(name = "value")
    @NonNull
    @Setter
    private Double value;

}
