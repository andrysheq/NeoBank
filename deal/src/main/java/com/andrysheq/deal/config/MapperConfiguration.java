package com.andrysheq.deal.config;

import com.andrysheq.deal.dto.CreditDTO;
import com.andrysheq.deal.entity.CreditEntity;
import com.andrysheq.deal.mapper.BaseMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.modelmapper.config.Configuration.AccessLevel.PUBLIC;

@Configuration
public class MapperConfiguration {

    @Bean
    public BaseMapper modelMapper() {
        BaseMapper mapper = new BaseMapper();

        mapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setAmbiguityIgnored(true)
                .setFieldAccessLevel(PUBLIC);
        //.setPropertyCondition(context -> !(context.getSource() instanceof PersistentCollection));

        createTypeMaps(mapper);

        return mapper;
    }

    private void createTypeMaps(BaseMapper mapper) {
        mapper.createTypeMap(CreditEntity.class, CreditDTO.class)
                .addMappings(expr -> {
                    expr.skip(CreditDTO::setAmount);
                    expr.skip(CreditDTO::setPsk);
                    expr.skip(CreditDTO::setRate);
                    expr.skip(CreditDTO::setTerm);
                    expr.skip(CreditDTO::setMonthlyPayment);
                    expr.skip(CreditDTO::setIsInsuranceEnabled);
                    expr.skip(CreditDTO::setIsSalaryClient);
                    expr.skip(CreditDTO::setPaymentSchedule);
                })
                .setPostConverter(
                        ctx -> {
                            final CreditEntity source = ctx.getSource();
                            final CreditDTO destination = ctx.getDestination();

                            destination.setPsk(source.getPsk());
                            destination.setAmount(source.getAmount());
                            destination.setTerm(source.getTerm());
                            destination.setRate(source.getRate());
                            destination.setIsInsuranceEnabled(source.getIsInsuranceEnabled());
                            destination.setIsSalaryClient(source.getIsSalaryClient());
                            destination.setPaymentSchedule(source.getPaymentSchedule());
                            destination.setMonthlyPayment(source.getMonthlyPayment());

                            return ctx.getDestination();
                        });

        mapper.createTypeMap(CreditDTO.class, CreditEntity.class)
                .addMappings(expr -> {
                    expr.skip(CreditEntity::setAmount);
                    expr.skip(CreditEntity::setPsk);
                    expr.skip(CreditEntity::setRate);
                    expr.skip(CreditEntity::setTerm);
                    expr.skip(CreditEntity::setMonthlyPayment);
                    expr.skip(CreditEntity::setIsInsuranceEnabled);
                    expr.skip(CreditEntity::setIsSalaryClient);
                    expr.skip(CreditEntity::setPaymentSchedule);
                })
                .setPostConverter(
                        ctx -> {
                            final CreditDTO source = ctx.getSource();
                            final CreditEntity destination = ctx.getDestination();

                            destination.setPsk(source.getPsk());
                            destination.setAmount(source.getAmount());
                            destination.setTerm(source.getTerm());
                            destination.setRate(source.getRate());
                            destination.setIsInsuranceEnabled(source.getIsInsuranceEnabled());
                            destination.setIsSalaryClient(source.getIsSalaryClient());
                            destination.setPaymentSchedule(source.getPaymentSchedule());
                            destination.setMonthlyPayment(source.getMonthlyPayment());

                            return ctx.getDestination();
                        });
    }
}
