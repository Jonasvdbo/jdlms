/*
 * Copyright 2012-2022 Fraunhofer ISE
 *
 * This file is part of jDLMS.
 * For more information visit http://www.openmuc.org
 *
 * jDLMS is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * jDLMS is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with jDLMS.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.openmuc.jdlms.internal.cli;

abstract class ValueCliParameter extends CliParameter {

    String parameterName;

    ValueCliParameter(CliParameterBuilder builder, String parameterName) {
        super(builder);
        this.parameterName = parameterName;
    }

    @Override
    int appendSynopsis(StringBuilder sb) {
        int length = 0;
        if (optional) {
            sb.append("[");
            length++;
        }
        sb.append(name).append(" <").append(parameterName).append(">");
        length += (name.length() + 3 + parameterName.length());
        if (optional) {
            sb.append("]");
            length++;
        }
        return length;
    }

    @Override
    void appendDescription(StringBuilder sb) {
        sb.append("\t").append(name).append(" <").append(parameterName).append(">\n\t    ").append(description);
    }
}
