#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
ROOT_DIR="$(cd "${SCRIPT_DIR}/.." && pwd)"
MAVEN_HOME_DIR="${ROOT_DIR}/.maven"

fetch_latest_maven_version() {
  curl -fsSL "https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/maven-metadata.xml" \
    | sed -n 's:.*<release>\(.*\)</release>.*:\1:p' \
    | sed -n '1p'
}

download_maven() {
  local version="$1"
  local major_version="${version%%.*}"
  local target_dir="${MAVEN_HOME_DIR}/apache-maven-${version}"
  local archive_name="apache-maven-${version}-bin.tar.gz"
  local primary_url="https://dlcdn.apache.org/maven/maven-${major_version}/${version}/binaries/${archive_name}"
  local fallback_url="https://archive.apache.org/dist/maven/maven-${major_version}/${version}/binaries/${archive_name}"
  local archive_path

  if [[ -x "${target_dir}/bin/mvn" ]]; then
    echo "Using existing local Maven ${version}."
    return
  fi

  mkdir -p "${MAVEN_HOME_DIR}"
  archive_path="$(mktemp)"

  echo "Downloading Apache Maven ${version}..."
  if ! curl -fL "${primary_url}" -o "${archive_path}"; then
    echo "Primary download URL unavailable; trying archive mirror..."
    if ! curl -fL "${fallback_url}" -o "${archive_path}"; then
      rm -f "${archive_path}"
      echo "Unable to download Apache Maven ${version}."
      exit 1
    fi
  fi

  tar -xzf "${archive_path}" -C "${MAVEN_HOME_DIR}"
  rm -f "${archive_path}"
}

run_build() {
  local mvn_cmd="$1"
  shift || true

  echo "Building project with: ${mvn_cmd}"
  "${mvn_cmd}" clean package "$@"
}

main() {
  local maven_version
  local mvn_cmd

  maven_version="$(fetch_latest_maven_version)"
  if [[ -z "${maven_version}" ]]; then
    echo "Unable to resolve the latest Apache Maven version."
    exit 1
  fi

  download_maven "${maven_version}"
  mvn_cmd="${MAVEN_HOME_DIR}/apache-maven-${maven_version}/bin/mvn"

  if [[ ! -x "${mvn_cmd}" ]]; then
    echo "Maven executable not found after download: ${mvn_cmd}"
    exit 1
  fi

  run_build "${mvn_cmd}" "$@"
}

main "$@"
