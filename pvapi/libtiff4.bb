DESCRIPTION = "libtiff4"
LICENSE = "CLOSED"
DEPENDS = "tiff"
PR = "r0"

PACKAGES = "${PN}"

do_install () {
	install -d ${D}/${libdir}

	# Required symbolic link: libtiff.so.4->libtiff.so.5.2.0
	ln -sf libtiff.so.5.2.0 ${D}${libdir}/libtiff.so.4
}

RPROVIDES_${PN} += "libtiff.so.4()(64bit)"

FILES_${PN} += "${libdir}/libtiff.so.4"
