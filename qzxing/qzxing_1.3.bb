DESCRIPTION = "QtQuick 2.0 port of the ZXing multi-format 1D/2D barcode image processing library"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://debian/copyright;md5=bd32546abe31bbd24ac5a995713f1c9c"
DEPENDS = "qtdeclarative"
PR = "r0"
SRC_URI = "git://github.com/dplanella/qzxing.git;branch=master"
SRCREV = "8dc842e6481f5bf947d13f21d8b919a854bf3068"
S = "${WORKDIR}/git"

INHIBIT_PACKAGE_STRIP = "1"

inherit qmake5

# Set path of qt5 headers as qmake5_base.bbclass sets this to just ${includedir} but
# actually it is ${includedir}/qt5
OE_QMAKE_PATH_HEADERS = "${OE_QMAKE_PATH_QT_HEADERS}"

do_install () {
        # Run qmake (compiled files will be placed in ${WORKDIR}/build/)
        oe_runmake install
        # Create directories
        install -d ${D}/${includedir}
        # Copy header files into /usr/include/
        install -m 0755 ${WORKDIR}/git/qzxing.h ${D}/${includedir}/qzxing.h
        install -m 0755 ${WORKDIR}/git/qzxing_global.h ${D}/${includedir}/qzxing_global.h
}

FILES_${PN} += "${includedir}/qzxing.h"
FILES_${PN} += "${includedir}/qzxing_global.h"
