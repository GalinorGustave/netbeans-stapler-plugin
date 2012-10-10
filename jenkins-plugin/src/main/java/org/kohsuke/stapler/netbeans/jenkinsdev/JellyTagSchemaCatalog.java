/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2010 Oracle and/or its affiliates. All rights reserved.
 *
 * Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html
 * or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header
 * Notice in each file and include the License file at
 * nbbuild/licenses/CDDL-GPL-2-CP.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * If you wish your version of this file to be governed by only the CDDL
 * or only the GPL Version 2, indicate your decision by adding
 * "[Contributor] elects to include this software in this distribution
 * under the [CDDL or GPL Version 2] license." If you do not indicate a
 * single choice of license, a recipient has the option to distribute
 * your version of this file under either the CDDL, the GPL Version 2 or
 * to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL
 * Version 2 license, then the option applies only if the new code is
 * made subject to such option by the copyright holder.
 *
 * Contributor(s):
 *
 * Portions Copyrighted 2009 Sun Microsystems, Inc.
 */

package org.kohsuke.stapler.netbeans.jenkinsdev;

import java.awt.Image;
import java.beans.PropertyChangeListener;
import java.util.Collections;
import java.util.Iterator;
import org.netbeans.modules.xml.catalog.spi.CatalogDescriptor;
import org.netbeans.modules.xml.catalog.spi.CatalogListener;
import org.netbeans.modules.xml.catalog.spi.CatalogReader;
import org.openide.filesystems.MIMEResolver;
import org.openide.util.ImageUtilities;
import org.openide.util.NbBundle;
import org.openide.util.lookup.ServiceProvider;

/**
 * Makes it possible to get completion on Jelly tags.
 * @see <a href="http://wiki.hudson-ci.org/display/HUDSON/Writing+Jelly+views+with+IDE+assistance">wiki</a>
 */
@MIMEResolver.NamespaceRegistration(
    displayName="",
    position=454,
    mimeType="text/x-jelly+xml",
    acceptedExtension="jelly",
    elementName="jelly",
    elementNS="jelly:core"
)
@ServiceProvider(service=CatalogReader.class, path="Plugins/XML/UserCatalogs", supersedes="org.netbeans.modules.hudson.maven.JellyTagSchemaCatalog")
public class JellyTagSchemaCatalog implements CatalogReader, CatalogDescriptor {

    public String resolveURI(String name) {
        name = name.replaceFirst("\\?.+", ""); // for some reason, get e.g. "jelly:define?fetch=false&&sync=true" here
        if (name.equals("jelly:stapler")) { // NOI18N
            return "https://stapler.dev.java.net/taglib.xsd"; // NOI18N
        } else if (name.startsWith("jelly:")) { // NOI18N
            return "jar:https://maven-jellydoc-plugin.dev.java.net/jelly-schemas.zip!/schemas/" + name.substring(6) + ".xsd"; // NOI18N
        } else {
            return null;
        }
    }

    @NbBundle.Messages("JellyTagSchemaCatalog.displayName=Jelly Tags")
    public String getDisplayName() {
        return Bundle.JellyTagSchemaCatalog_displayName();
    }

    @NbBundle.Messages("JellyTagSchemaCatalog.shortDescription=Offers schemas for standard Jelly tags and those defined by Stapler.")
    public String getShortDescription() {
        return Bundle.JellyTagSchemaCatalog_shortDescription();
    }

    public Image getIcon(int type) {
        return ImageUtilities.loadImage("org/netbeans/modules/xml/catalog/resources/catalog-root.gif", true);
    }

    public Iterator getPublicIDs() {
        return Collections.EMPTY_SET.iterator();
    }

    public void refresh() {}

    public String getSystemID(String publicId) {
        return null;
    }

    public String resolvePublic(String publicId) {
        return null;
    }

    public void addCatalogListener(CatalogListener l) {}

    public void removeCatalogListener(CatalogListener l) {}

    public void addPropertyChangeListener(PropertyChangeListener l) {}

    public void removePropertyChangeListener(PropertyChangeListener l) {}

}
