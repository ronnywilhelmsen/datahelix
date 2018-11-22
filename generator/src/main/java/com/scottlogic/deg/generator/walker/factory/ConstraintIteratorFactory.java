package com.scottlogic.deg.generator.walker.factory;

import com.scottlogic.deg.generator.decisiontree.ConstraintNode;
import com.scottlogic.deg.generator.decisiontree.DecisionNode;
import com.scottlogic.deg.generator.walker.RouteConstraintIterator;
import com.scottlogic.deg.generator.walker.RouteDecisionIterator;
import com.scottlogic.deg.generator.walker.EndDecisionIterator;
import com.scottlogic.deg.generator.walker.LeafConstraintIterator;

import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Collectors;

public class ConstraintIteratorFactory {

    public static ConstraintIterator create(ConstraintNode constraintNode){
        return createConstraintIterator(constraintNode);
    }

    private static ConstraintIterator createConstraintIterator(ConstraintNode constraintNode){
        if (constraintNode.getDecisions().isEmpty()){
            return new LeafConstraintIterator(constraintNode);
        }

        DecisionIterator decisions = createDecisionIterator(constraintNode.getDecisions().iterator());
        return new RouteConstraintIterator(decisions, constraintNode);
    }


    private static DecisionIterator createDecisionIterator(Iterator<DecisionNode> decisionNodes){
        if (decisionNodes == null || !decisionNodes.hasNext())
            return null;

        Collection<ConstraintIterator> options = decisionNodes.next()
            .getOptions().stream()
            .map(ConstraintIteratorFactory::createConstraintIterator)
            .collect(Collectors.toSet());

        if(!decisionNodes.hasNext()) {
            return new EndDecisionIterator(options);
        }

        return new RouteDecisionIterator(options, createDecisionIterator(decisionNodes));
    }

}
