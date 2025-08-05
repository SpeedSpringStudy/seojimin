    package com.spring.study.domain.entity;

    import com.spring.study.common.exception.ErrorCode;
    import com.spring.study.common.exception.custonException.BusinessException;
    import jakarta.persistence.Entity;
    import jakarta.persistence.GeneratedValue;
    import jakarta.persistence.GenerationType;
    import jakarta.persistence.Id;
    import jakarta.persistence.JoinColumn;
    import jakarta.persistence.ManyToOne;
    import jakarta.persistence.Version;
    import lombok.AccessLevel;
    import lombok.AllArgsConstructor;
    import lombok.Builder;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import org.springframework.security.core.parameters.P;

    @Entity
    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public class ProductOption {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "product", nullable = false)
        private Product product;

        @ManyToOne
        @JoinColumn(name = "option", nullable = false)
        private Option option;

        private int quantity;

        @Version
        private Long version;

        public ProductOption updateQuantity(int quantity){
            this.quantity = quantity;
            return this;
        }

        public void decreaseQuantity(int quantity){
            if (this.quantity < quantity){
                throw new BusinessException(ErrorCode.QUANTITY_OVER);
            }
            System.out.printf("재고 차감 %d -> %d\n",this.quantity, this.quantity - quantity);
            this.quantity -= quantity;
        }
    }
